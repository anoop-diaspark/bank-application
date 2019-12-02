package com.diaspark.INB.service.impl;

import com.diaspark.INB.DTO.*;
import com.diaspark.INB.config.JwtTokenProvider;
import com.diaspark.INB.entity.*;

import com.diaspark.INB.exception.EmailSendException;
import com.diaspark.INB.exception.ForbiddenException;
import com.diaspark.INB.exception.NotFoundException;
import com.diaspark.INB.mapper.EntityToDTOMapper;
import com.diaspark.INB.repository.AccountSequenceRepository;
import com.diaspark.INB.repository.CustomerSequenceRepository;
import com.diaspark.INB.repository.UserAccountRepository;
import com.diaspark.INB.repository.UserRepository;

import com.diaspark.INB.service.ContactUsMailService;
import com.diaspark.INB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
@Autowired
private AccountSequenceRepository accountsequencerepository;
@Autowired
private CustomerSequenceRepository customersequencerepository;


    @Autowired
    private EntityToDTOMapper entityToDTOMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    private ContactUsMailService notificationService;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {
        User newUser = createRegisterUser(registerUserDTO);
        User saveuser = userRepository.save(newUser);
        UserAccount userAccount = createAccount(registerUserDTO, saveuser);
        userAccountRepository.save(userAccount);
    }

    /*
     * This function is transformation on input dto to registerUser enity bean
     */
    private User createRegisterUser(RegisterUserDTO registerUserDTO) {
        User newUser = new User();
        newUser.setPassword(registerUserDTO.getPassword());
        newUser.setFirstName(registerUserDTO.getFirstName());
        newUser.setLastName(registerUserDTO.getLastName());
        newUser.setAddressLine1(registerUserDTO.getAddressLine1());
        newUser.setAddressLine2(registerUserDTO.getAddressLine2());
        newUser.setAddressLine3(registerUserDTO.getAddressLine3());
        newUser.setCell(registerUserDTO.getMobile());
        newUser.setEmail(registerUserDTO.getEmail());
        newUser.setState(registerUserDTO.getState());
        newUser.setZip(registerUserDTO.getZip());
        newUser.setCity(registerUserDTO.getCity());
        newUser.setPhone(registerUserDTO.getPhone());
        newUser.setUsername(registerUserDTO.getUsername());
        CustomerSequence customersequence = customersequencerepository.findByName("customersequence");
          newUser.setId(customersequence.getId()+1);
      customersequence.setId(customersequence.getId()+1);
      customersequencerepository.save(customersequence);

        if(registerUserDTO.isAdmin()){
            newUser.setRole(Role.ADMIN.getRole());
            newUser.setStatus(UserStatus.APPROVED.getStatus());
        }else{
            newUser.setRole(Role.USER.getRole());
            newUser.setStatus(UserStatus.PENDING.getStatus());
        }
        return newUser;
    }

    @Override
    public UserResponseDTO authenticateUser(LoginUserDTO loginUserDTO) throws Exception {
        User existingUser = userRepository.findUserByUsername(loginUserDTO.getUsername());

        if(existingUser == null){
            throw new ForbiddenException("username is not present");
        }

        if(existingUser.getRetryCount() >= 3){
            throw new ForbiddenException("you have reached you maximum login attempt");
        }

        if ((!existingUser.getPassword().equals(loginUserDTO.getPassword())
                || !UserStatus.APPROVED.getStatus().equals(existingUser.getStatus()))) {
            existingUser.setRetryCount(existingUser.getRetryCount() + 1);
            userRepository.save(existingUser);
            throw new ForbiddenException("username or password is incorrect");
        }

        if(loginUserDTO.isAdmin() && (existingUser.getRole().equals(Role.USER.getRole()))){
            throw new ForbiddenException("you are not an Admin to access admin portal");
            
        }

        if(!loginUserDTO.isAdmin() && (existingUser.getRole().equals(Role.ADMIN.getRole()))){
            throw new ForbiddenException("you cannot access user portal");
        }

        if(!UserStatus.APPROVED.getStatus().equals(existingUser.getStatus())){
            throw new ForbiddenException("your user is not approved");
        }

        // creating UserPrincipal object to set in token
        UserPrincipal userPrincipal = entityToDTOMapper.buildUserPrincipalFromUser(existingUser, jwtExpirationInMs);

        // Generating JWT token
        String token = jwtTokenProvider.generateToken(userPrincipal);
        List<AccountResponseDTO> accountResponses = new ArrayList<>();
        List<UserAccount> userAccountList = userAccountRepository.findUserAccountByUser(existingUser);
        for (UserAccount userAccount : userAccountList) {
            AccountResponseDTO accountResponseDTO = entityToDTOMapper.buildAccountResponseDto(userAccount);
            accountResponses.add(accountResponseDTO);
        }

        // building UserResponse Dto to sendQuery json back to client
        UserResponseDTO userResponseDTO = entityToDTOMapper.buildUserResponseDTO(existingUser);
        userResponseDTO.setToken(token);
        userResponseDTO.setAccountResponseDTO(accountResponses);
        // adding token to cookie
        httpServletResponse.addCookie(buildCookies("X-AUTH-TOKEN", token));
        return userResponseDTO;
    }


    private Cookie buildCookies(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(1600);
        cookie.setHttpOnly(true);
        return cookie;
    }

    private UserAccount createAccount(RegisterUserDTO registerUserDto, User user) {
        AccountType.findAccountByDescritpion(String.valueOf(registerUserDto.getAccountType()));
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountBalance(0);
        userAccount.setAccountType(registerUserDto.getAccountType());
        userAccount.setUser(user);
        AccountSequence accountsequence = accountsequencerepository.findByName("accountsequence");
        userAccount.setAccountNumber(accountsequence.getId()+1);
        accountsequence.setId(accountsequence.getId()+1);
        accountsequencerepository.save(accountsequence);

        return userAccount;
    }

    public EmailResponseDTO sendQuery(SendMailDTO sendMailDTO) {
        /*
         * Creating a User with the help of User class that we have declared and setting
         * Email address of the sender.
         */
        sendMailDTO.setToAddress("IndianNetBank3A@gmail.com"); // Receiver's email address
        sendMailDTO.setSubject(sendMailDTO.getFirstName() + " " + sendMailDTO.getLastName() + " has following query");
        /*
         * Here we will call sendEmail() for Sending mail to the sender.
         */
        EmailResponseDTO emailResponseDTO = new EmailResponseDTO();
        try {
            notificationService.sendEmail(sendMailDTO);
            emailResponseDTO.setContactUsResponse("Email sent Succesfully");
        } catch (MailException mailException) {
            throw new EmailSendException("Failed to send email");
        }

        return emailResponseDTO;

    }

    @Override
    public List<UserResponseDTO> retreiveUsers(String status) {

        // Validate if incoming status is a valid status
        UserStatus.findCodeByStatus(status);
        List<UserResponseDTO> userNames = new ArrayList<>();

        // Search users by status
        List<User> userList = userRepository.findUserByStatus(status);
        for (User user : userList) { // for each loop/ how to use this in java 8
            UserResponseDTO userResponseDTO = entityToDTOMapper.buildUserResponseDTO(user);
            userNames.add(userResponseDTO);
        }
        return userNames;

    }


    @Override
    public UserResponseDTO updateUserStatus(long customerId, String status) {
        // Validate if incoming status is a valid status
        UserStatus.findCodeByStatus(status);

        // Find existingUser which need to be updated
        User existingUser = userRepository.findUserById(customerId);
        if (existingUser == null) {
            throw new NotFoundException("Customer Id is invalid");
        }
        existingUser.setStatus(status);
        // update user with new status
        User savedUser = userRepository.save(existingUser);
        sendMailIfApproved(savedUser);
        sendMailIfRejected(savedUser);
        return entityToDTOMapper.buildUserResponseDTO(savedUser);
    }

    private void sendMailIfApproved(User user) {
        if (UserStatus.APPROVED.getStatus().equals(user.getStatus())) {
            SendMailDTO sendMailDTO = new SendMailDTO();
            sendMailDTO.setToAddress(user.getEmail());
            sendMailDTO.setSubject("Your account is activated");
            sendMailDTO.setQueries("Dear Customer, you account has been successfully validated." + " " +  " " + "Login to use INB services.");
            notificationService.sendEmail(sendMailDTO);
        }
    }

    private void sendMailIfRejected(User user) {
        if (UserStatus.REJECTED.getStatus().equals(user.getStatus())) {
            SendMailDTO sendMailDTO = new SendMailDTO();
            sendMailDTO.setToAddress(user.getEmail());
            sendMailDTO.setSubject("Your account is rejected");
            sendMailDTO.setQueries("Dear Customer, your account is not being created due to some error. We are very sorry for the inconvinience.");
            notificationService.sendEmail(sendMailDTO);

        }
    }

    public void getAccountsByCustomer(long customerId) {
        // Find existingUser which need to be updated
        User existingUser = userRepository.findUserById(customerId);
        if (existingUser == null) {
            throw new NotFoundException("Customer Id is invalid");
        }

        List<UserAccount> userAccountList = userAccountRepository.findUserAccountByUser(existingUser);
    }
}

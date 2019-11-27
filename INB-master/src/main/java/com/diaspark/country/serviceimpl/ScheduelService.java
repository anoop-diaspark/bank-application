 package com.diaspark.country.serviceimpl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import com.diaspark.country.countrydto.MatchDetailsDTO;
import com.diaspark.country.countrydto.MatchScheduleDTO;
import com.diaspark.country.entity.ResultEntity;
import com.diaspark.country.entity.ScheduleEntity;
import com.diaspark.country.enums.MatchStatus;
import com.diaspark.country.enums.MatchType;

import com.diaspark.country.mapper.EntityToDTOMapper;

import com.diaspark.country.repository.ScheduelRepository;
import com.diaspark.country.service.MatchService;

@Service
public class ScheduelService implements MatchService {

	@Autowired
	private ScheduelRepository scheduelRepository;

	@Autowired
	private EntityToDTOMapper entityToDTOMapper;
	


	/* this is the function which save the match schedule data in table */
	@Override 
	public MatchScheduleDTO matchlist(MatchScheduleDTO matchScheduleDTO) {
		MatchStatus.findCodeBymatchResult(matchScheduleDTO.getMatchStatus());
		MatchType.findMatchTypeByType(matchScheduleDTO.getMatchType());
		ScheduleEntity schedule = new ScheduleEntity();
		ScheduleEntity schedule1 = new ScheduleEntity();
		ResultEntity result = new ResultEntity();

		schedule.setCountryName(matchScheduleDTO.getCountryName());
		schedule.setDate(matchScheduleDTO.getMatchDate());

		result.setMatchStatus(matchScheduleDTO.getMatchStatus());
		schedule.setResult(result); 
		schedule.setMatchType(matchScheduleDTO.getMatchType());
		schedule1 = scheduelRepository.save(schedule);


		return entityToDTOMapper.buildMatchResponseDTO(schedule1);
	}


	@Override
	public List<MatchScheduleDTO> retrieveallMatchlDetails(int pageNumber, MatchDetailsDTO matchDetailsDTO) {
		// TODO Auto-generated method stub
		List<MatchScheduleDTO> allCountryData = new ArrayList<>();
		Pageable pageRequest = PageRequest.of(0, pageNumber);
		if(matchDetailsDTO.getCountryName().isEmpty() || matchDetailsDTO.getMatchType().isEmpty()){
			Page<ScheduleEntity> matchList = scheduelRepository.findScheduleEntityByCountryNameInOrMatchTypeIn(
					matchDetailsDTO.getCountryName(), matchDetailsDTO.getMatchType(), pageRequest);
			for (ScheduleEntity matchOf : matchList) {
				MatchScheduleDTO countryResponseDTO = entityToDTOMapper.buildMatchResponseDTO(matchOf);
				allCountryData.add(countryResponseDTO);
 
			} 
		 	 
		}
		else{
			Page<ScheduleEntity> matchList = scheduelRepository.findScheduleEntityByCountryNameInAndMatchTypeIn(
					matchDetailsDTO.getCountryName(), matchDetailsDTO.getMatchType(), pageRequest);
			for (ScheduleEntity matchOf : matchList) {
				MatchScheduleDTO countryResponseDTO = entityToDTOMapper.buildMatchResponseDTO(matchOf);
				allCountryData.add(countryResponseDTO);

			}
		}
		

	

		return allCountryData;

	}

	/* this is the function which delete one row data */
	@Override
	public List<ScheduleEntity> deleteOneRecord(Long id, int pageNumber) {
	
		  List<ScheduleEntity> s = scheduelRepository.removeScheduleDAOById(id);
		  return s;
}



	/* this function update the one row recored by id */
	@Override
	public MatchScheduleDTO updateMatchSchedule(Long id, String countryName, String matchType, String matchDate,
			String matchStatus) {
		ScheduleEntity scheduleDAO = scheduelRepository.findScheduleDAOById(id);
		if (countryName == "") {

		} else {
			scheduleDAO.setCountryName(countryName);
		}
		if (matchType == "") {

		} else {
			MatchType.findMatchTypeByType(matchType);
			scheduleDAO.setMatchType(matchType);
			
		}
		if (matchDate == "") {

		} else {
			scheduleDAO.setDate(matchDate);

		}
		if (matchStatus == "") {  

		} else {
			MatchStatus.findCodeBymatchResult(matchStatus);
			scheduleDAO.getResult().setMatchStatus(matchStatus);
		}
		ScheduleEntity savedDetails = scheduelRepository.save(scheduleDAO);
		return entityToDTOMapper.buildMatchResponseDTO(savedDetails);
	}

}

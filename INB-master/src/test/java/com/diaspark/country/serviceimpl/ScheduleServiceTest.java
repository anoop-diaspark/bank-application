
package com.diaspark.country.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.diaspark.country.countrydto.MatchScheduleDTO;
import com.diaspark.country.entity.ResultEntity;
import com.diaspark.country.entity.ScheduleEntity;

import com.diaspark.country.mapper.EntityToDTOMapper;
import com.diaspark.country.repository.ScheduelRepository;
import com.diaspark.country.countrydto.MatchDetailsDTO;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ScheduleServiceTest {

	@InjectMocks
	private ScheduelService scheduelService;

	@Mock
	private ScheduelRepository scheduelRepository;

	@Mock
	private EntityToDTOMapper entityToDTOMapper;

	@Mock
	private Pageable pageableMock;

	@Mock
	private Page<ScheduleEntity> schedulePageEntities;

	@Mock
	private List<ScheduleEntity> scheduleListEntities;

	@Mock
	private List<MatchScheduleDTO> matchScheduleListDTO;
	@Mock
	private ScheduleEntity ScheduleEntity;
	@Mock
 private MatchScheduleDTO matchScheduleDTO;
	
/*	@Mock
	private MatchDetailsDTO matchDetailsDTO;*/
	
	@Mock
	private  Set<String> countryNames;
	
	@Mock
	private  Set<String> matchTypes;
	
	@Before
	public void setUp() throws Exception {  
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void registerationTest() {
		MatchScheduleDTO matchScheduleDTO = new MatchScheduleDTO();
		matchScheduleDTO.setCountryName("India");
		matchScheduleDTO.setId((long) 79);
		matchScheduleDTO.setMatchDate("09//08/2020");
		matchScheduleDTO.setMatchStatus("upcoming");
		matchScheduleDTO.setMatchType("odi"); 

		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setId((long) 79);
		resultEntity.setMatchStatus("upcoming");

		ScheduleEntity scheduleEntity = new ScheduleEntity();
		scheduleEntity.setCountryName("India");
		scheduleEntity.setId((long) 79);
		scheduleEntity.setDate("09//08/2020");
		scheduleEntity.setResult(resultEntity);
		scheduleEntity.setMatchType("odi");

		Mockito.when(scheduelRepository.save(scheduleEntity)).thenReturn(scheduleEntity);
		Mockito.when(entityToDTOMapper.buildMatchResponseDTO(scheduleEntity)).thenReturn(matchScheduleDTO);
		Mockito.when(scheduelService.matchlist(matchScheduleDTO)).thenReturn(matchScheduleDTO);

	}

	@Test
	public void retrieveallMatchlDetailTest() {
		int pageNumber = 0;
		int pageSize = 8;
	List<MatchScheduleDTO> allCountryData = new ArrayList<>();
		 Set<String> countryName = new HashSet<>(Arrays.asList("efh","sdfg"));
		 Set<String> matchType = new HashSet<>(Arrays.asList("edfg","fdg"));
		 MatchDetailsDTO matchDetailsDTO = new MatchDetailsDTO();
		 matchDetailsDTO.setCountryName(countryName);
		 matchDetailsDTO.setMatchType(matchType);
		MatchScheduleDTO matchScheduleDTO = new MatchScheduleDTO();
		matchScheduleDTO.setCountryName("India");
		matchScheduleDTO.setId((long) 79);
		matchScheduleDTO.setMatchDate("09//08/2020");
		matchScheduleDTO.setMatchStatus("upcoming");
		matchScheduleDTO.setMatchType("odi");

		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setId((long) 79);
		resultEntity.setMatchStatus("upcoming");

		ScheduleEntity scheduleEntity = new ScheduleEntity();
		scheduleEntity.setCountryName("India");
		scheduleEntity.setId((long) 79);
		scheduleEntity.setDate("09//08/2020");
		scheduleEntity.setResult(resultEntity);
		scheduleEntity.setMatchType("odi"); 
		allCountryData.add(matchScheduleDTO);
		Pageable pageable = PageRequest.of(0, 8);
		allCountryData.add(matchScheduleDTO); 
		Mockito.when(countryNames.isEmpty()).thenReturn(true);
		Mockito.when(matchTypes.isEmpty()).thenReturn(true);
		Mockito.when(scheduelRepository.findScheduleEntityByCountryNameInAndMatchTypeIn(countryName, matchType, pageable)).thenReturn(schedulePageEntities);
		Mockito.when(scheduelRepository.findScheduleEntityByCountryNameInOrMatchTypeIn(countryName, matchType, pageable)).thenReturn(schedulePageEntities);
		Mockito.when(entityToDTOMapper.buildMatchResponseDTO(scheduleEntity)).thenReturn(matchScheduleDTO);
		 assertEquals(2, allCountryData.size());
		  ArgumentCaptor<Pageable> pageableCaptor = 
			        ArgumentCaptor.forClass(Pageable.class);
		    verify(scheduelRepository).findScheduleEntityByCountryNameInAndMatchTypeIn(countryName, matchType,pageableCaptor.capture());
		   
			
			PageRequest pageable1 = (PageRequest) pageableCaptor.getValue();
		  assertThat(pageable1).isEqualTo(pageNumber);
		  assertThat(pageable1).isEqualTo(pageSize);
		
		 
		/* Page<ScheduleEntity> tasks = Mockito.mock(Page.class);
		 Mockito.when(scheduelRepository.findScheduleEntityByCountryNameInAndMatchTypeIn(countryName, matchType,org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(tasks);
		 */
		/* List<ScheduleEntity> tasks = new ArrayList<>();
		
		 Page<ScheduleEntity> pagedTasks = new PageImpl(tasks);
		 Mockito.when(scheduelRepository.findScheduleEntityByCountryNameInOrMatchTypeIn(countryName, matchType,pagedTasks)).thenReturn(pagedTasks);*/
		Mockito.when(scheduelService.retrieveallMatchlDetails(pageNumber, matchDetailsDTO)).thenReturn(allCountryData);
	}
	@Test
	public void deleteoneRecoredTest() {
		long id = 20; 
		int pageNumber = 1;


	Mockito.when(scheduelService.deleteOneRecord(id, pageNumber)).thenReturn(scheduleListEntities);

	}
	@Test
	public void updateMatchSchceduleTest(){
		long id = 20;
		String countryName = "India"; 
		String matchType = "odi";
		String matchDate ="01/09.2018";
		String matchStatus = "lose";
		
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setId((long) 79);
		resultEntity.setMatchStatus("upcoming");

		ScheduleEntity scheduleEntity = new ScheduleEntity();
		scheduleEntity.setCountryName("India");
		scheduleEntity.setId((long) 79);
		scheduleEntity.setDate("09//08/2020");
		scheduleEntity.setResult(resultEntity);
		scheduleEntity.setMatchType("odi");
		Mockito.when(scheduelRepository.findScheduleDAOById(id)).thenReturn(scheduleEntity);

		
		Mockito.when(scheduelRepository.save(scheduleEntity)).thenReturn(scheduleEntity);
		Mockito.when(scheduelService.updateMatchSchedule(id, countryName, matchType, matchDate, matchStatus)).thenReturn(matchScheduleDTO);
	}
}

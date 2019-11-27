package com.diaspark.country.service;

import java.util.List;

import com.diaspark.country.countrydto.MatchDetailsDTO;
import com.diaspark.country.countrydto.MatchScheduleDTO;
import com.diaspark.country.entity.ScheduleEntity;

public interface MatchService {
	MatchScheduleDTO matchlist(MatchScheduleDTO matchScheduleDTO);
/*	 List<MatchScheduleDTO> retrieveallMatchlDetail(int pageNumber);*/
	List<ScheduleEntity>  deleteOneRecord(Long id,int pageNumber);
	 MatchScheduleDTO updateMatchSchedule(Long id,String countryName,String matchType,String matchDate,String matchStatus);
	
	 List<MatchScheduleDTO> retrieveallMatchlDetails(int pageNumber,MatchDetailsDTO matchDetailsDTO);
}

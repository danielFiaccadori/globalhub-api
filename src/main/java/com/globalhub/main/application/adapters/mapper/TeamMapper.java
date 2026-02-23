package com.globalhub.main.application.adapters.mapper;

import com.globalhub.main.application.dto.team.TeamDetailsDTO;
import com.globalhub.main.domain.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDetailsDTO toTeamDetailsDTO(Team team);

}

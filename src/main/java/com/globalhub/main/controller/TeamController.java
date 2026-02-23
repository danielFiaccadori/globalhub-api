package com.globalhub.main.controller;

import com.globalhub.main.application.dto.team.TeamCreationRequestDTO;
import com.globalhub.main.application.dto.team.TeamDetailsDTO;
import com.globalhub.main.application.dto.team.UpdateTeamRequestDTO;
import com.globalhub.main.application.response.BaseResponse;
import com.globalhub.main.application.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Page<TeamDetailsDTO>>> findAll(Pageable pageable) {
        Page<TeamDetailsDTO> teams = service.findAll(pageable);
        return ResponseEntity.ok().body(BaseResponse.ok(teams));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BaseResponse<TeamDetailsDTO>> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(BaseResponse.ok(service.findById(uuid)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<TeamDetailsDTO>> createTeam(@RequestBody @Valid TeamCreationRequestDTO teamCreationRequestData) {
        return ResponseEntity.ok(BaseResponse.ok(service.createTeam(teamCreationRequestData)));
    }

    @PutMapping("/update/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<TeamDetailsDTO>> updateTeam(@PathVariable UUID uuid,
                                                                       @RequestBody @Valid UpdateTeamRequestDTO teamUpdateRequestData) {
        return ResponseEntity.ok(BaseResponse.ok(service.updateTeamDetails(uuid, teamUpdateRequestData)));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> deactivateTeam(@PathVariable UUID uuid) {
        service.updateActivity(uuid, false);
        return ResponseEntity.ok().body(BaseResponse.ok(null, "Team successfully deactivated ;("));
    }

    @PutMapping("/{uuid}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> activateTeam(@PathVariable UUID uuid) {
        service.updateActivity(uuid, true);
        return ResponseEntity.ok().body(BaseResponse.ok(null, "Team successfully reactivated :)"));
    }

}

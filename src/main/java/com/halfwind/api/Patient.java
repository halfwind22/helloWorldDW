package com.halfwind.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Patient {

    private final Long id;
    private final String name;
}
package com.example.fisherybackend.payloads.response;

import com.example.fisherybackend.enums.CommonResponseReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private String message;
    private boolean isValid = true;

    private CommonResponseReason commonResponseReason;

    public CommonResponse(String message) {
        this.message = message;
    }
}

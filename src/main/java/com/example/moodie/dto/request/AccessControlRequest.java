package com.example.moodie.dto.request;

import jakarta.annotation.Nonnull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccessControlRequest {
    @Nonnull
    private String url;
}

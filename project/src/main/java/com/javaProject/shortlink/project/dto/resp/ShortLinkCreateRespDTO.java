package com.javaProject.shortlink.project.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShortLinkCreateRespDTO {

    private String fullShortUrl;

    private String originUrl;

    private String gid;
}

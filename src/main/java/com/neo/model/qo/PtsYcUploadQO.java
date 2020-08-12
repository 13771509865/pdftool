package com.neo.model.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PtsYcUploadQO {

    private Integer  id;

    private Integer status;

    private Integer errorCode;

    private String fileHash;

    private Long userId;
}

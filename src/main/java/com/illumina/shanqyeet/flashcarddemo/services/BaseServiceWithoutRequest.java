package com.illumina.shanqyeet.flashcarddemo.services;

public interface BaseServiceWithoutRequest<BaseResponse> {
    com.illumina.shanqyeet.flashcarddemo.dtos.responses.BaseResponse execute();
}

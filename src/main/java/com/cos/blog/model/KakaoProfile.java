package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {
    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Getter
    @NoArgsConstructor
    public class Properties {
        public String nickname;
    }

    @Getter
    @NoArgsConstructor
    public class KakaoAccount {
        public Boolean profile_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;

        @Getter
        @NoArgsConstructor
        public class Profile {
            public String nickname;
        }
    }
}
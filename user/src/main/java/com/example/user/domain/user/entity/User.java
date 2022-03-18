package com.example.user.domain.user.entity;

import lombok.*;


/**
 * 【请填写功能名称】对象 user
 *
 * @author admin
 * @date 2022-03-12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private static final long serialVersionUID = 1L;

    /** 用户名 */
    private String username;

    /** 用户密码 */
    private String password;

    /** 手机号码 */
    private String phone;

}

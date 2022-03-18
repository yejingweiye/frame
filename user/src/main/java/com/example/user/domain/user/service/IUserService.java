package com.example.user.domain.user.service;

import com.example.user.domain.user.entity.User;

import java.util.List;


/**
 * 【请填写功能名称】Service接口
 *
 * @author admin
 * @date 2022-03-12
 */
public interface IUserService {
    /**
     * 查询【请填写功能名称】
     *
     * @param username 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public User selectUserById(String username);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param user 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<User> selectUserList(User user);

    /**
     * 新增【请填写功能名称】
     *
     * @param user 【请填写功能名称】
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 新增【请填写功能名称】
     *
     * @param userList 【请填写功能名称】
     * @return 结果
     */
    public int insertUserList(List<User> userList);

    /**
     * 修改【请填写功能名称】
     *
     * @param user 【请填写功能名称】
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param usernames 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteUserByIds(String[] usernames);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param username 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteUserById(String username);
}

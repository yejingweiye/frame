package com.example.user.domain.user.dao;

import java.util.List;

import com.example.user.domain.user.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author admin
 * @date 2022-03-12
 */
public interface UserMapper {
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
    public int insertUserList(@Param("list") List<User> userList);

    /**
     * 修改【请填写功能名称】
     *
     * @param user 【请填写功能名称】
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 删除【请填写功能名称】
     *
     * @param username 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteUserById(String username);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param usernames 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserByIds(String[] usernames);
}

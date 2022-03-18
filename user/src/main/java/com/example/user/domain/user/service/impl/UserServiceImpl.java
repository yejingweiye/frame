package com.example.user.domain.user.service.impl;

import java.util.List;
import javax.annotation.Resource;
import com.example.user.domain.user.dao.UserMapper;
import com.example.user.domain.user.entity.User;
import com.example.user.domain.user.service.IUserService;
import org.springframework.stereotype.Service;



/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author admin
 * @date 2022-03-12
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param username 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public User selectUserById(String username) {
        return userMapper.selectUserById(username);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param user 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param user 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param userList 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertUserList(List<User> userList) {
        return userMapper.insertUserList(userList);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param user 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param usernames 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String[] usernames) {
        return userMapper.deleteUserByIds(usernames);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param username 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteUserById(String username) {
        return userMapper.deleteUserById(username);
    }
}

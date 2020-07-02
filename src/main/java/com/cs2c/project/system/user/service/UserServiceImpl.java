package com.cs2c.project.system.user.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import com.cs2c.project.module.trustHost.domain.TrustHost;
import com.cs2c.project.module.trustHost.service.ITrustHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.aescode;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.aspectj.lang.annotation.DataScope;
import com.cs2c.framework.shiro.service.PasswordService;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.system.post.domain.Post;
import com.cs2c.project.system.post.mapper.PostMapper;
import com.cs2c.project.system.role.domain.Role;
import com.cs2c.project.system.role.mapper.RoleMapper;
import com.cs2c.project.system.user.domain.User;
import com.cs2c.project.system.user.domain.UserPost;
import com.cs2c.project.system.user.domain.UserRole;
import com.cs2c.project.system.user.mapper.UserMapper;
import com.cs2c.project.system.user.mapper.UserPostMapper;
import com.cs2c.project.system.user.mapper.UserRoleMapper;

/**
 * 用户 业务层处理
 * 
 * @author cs2c
 */
@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private ITrustHostService trustHostService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserPostMapper userPostMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordService passwordService;

    private codezip codeZip = new codezip();
    
    private aescode aes = new aescode();
    
    static Base64.Decoder decoder = Base64.getDecoder();
    static Base64.Encoder encoder = Base64.getEncoder();
    
    
    /**
     * 根据条件分页查询用户对象
     * 
     * @param user 用户信息
     * 
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(tableAlias = "u")
    public List<User> selectUserList(User user)
    {   // 生成数据权限过滤条件
        user = encode(user);
        //System.out.println("selectUserList。");
        if(user == null)
            return null;
        List<User> list = userMapper.selectUserList(user);
        for(User use : list) {
            use = decode(use);
        }
     
        return list;
    }

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByLoginName(String userName)
    {
        //System.out.println("selectUserByLoginName。");
        User user ;
        try {
            userName = aescode.encrypt(userName);
            //userName = enc(encoder.encodeToString(userName.getBytes("UTF-8")));
            user = decode(userMapper.selectUserByLoginName(userName));
            if (user == null) 
                return null;
        } catch (Exception e) {
            System.out.println("用户信息解密失败。");
            return null;
        }
        return user;
    }

    /**
     * 通过手机号码查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByPhoneNumber(String phoneNumber)
    {
        //System.out.println("selectUserByPhoneNumber。");
        User user ;
        try {
            phoneNumber = aescode.encrypt(phoneNumber);
            //phoneNumber = enc(encoder.encodeToString(phoneNumber.getBytes("UTF-8")));
            user = decode(userMapper.selectUserByPhoneNumber(phoneNumber));
            if (user == null) 
                return null;
        } catch (Exception e) {
            System.out.println("用户信息解密失败。");
            return null;
        }
        return user;
    }

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email)
    {
        //System.out.println("selectUserByEmail。");
        User user ;
        try {
            email = aescode.encrypt(email);
            //email = enc(encoder.encodeToString(email.getBytes("UTF-8")));
            user = decode(userMapper.selectUserByEmail(email));
            if (user == null) 
                return null;
        } catch (Exception e) {
            System.out.println("用户信息解密失败。");
            return null;
        }
        return user;
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId)
    {
        //System.out.println("selectUserById。");
        User user ;
        //System.out.println("selectUserById。:"+userId);
        user = decode(userMapper.selectUserById(userId));
        //System.out.println("selectUserById。:"+user);
        if (user == null) 
            return null;
        return user;
    }

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws Exception
    {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds)
        {
            if (User.isAdmin(userId))
            {
                throw new Exception("不允许删除超级管理员用户");
            }
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {   
        
        //System.out.println(user);
        int count;
        try {
            //count = userMapper.checkLoginNameUnique(enc(encoder.encodeToString(user.getLoginName().getBytes("UTF-8"))));
            count = userMapper.checkLoginNameUnique(aescode.encrypt(user.getLoginName()));
            User User = new User();
            if (count > 0) {
                //User = userMapper.selectUserByLoginName(enc(encoder.encodeToString(user.getLoginName().getBytes("UTF-8"))));
                User = userMapper.selectUserByLoginName(aescode.encrypt(user.getLoginName()));
                if ( User.getDelFlag().equals("2"))
                {
                    userMapper.deleteUserById(User.getUserId());
                }else {return 0;}
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        // 新增用户信息
        user = encode(user);
        if (user == null)
            return 0;
       
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        Long userId = user.getUserId();
        
        user.setUpdateBy(ShiroUtils.getLoginName());
        user.setLoginName("");
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        user = encode(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(User user)
    {
        user.setPassword(this.selectUserById(user.getUserId()).getPassword());
        user.setLoginName(this.selectUserById(user.getUserId()).getLoginName());
        
        //System.out.println("287 "+user.getLoginName());
        
        user = encode(user);
        //userMapper.updateUser(user);
        //user = decode(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user)
    {
        user.randomSalt();
        user.setLoginName(decode(userMapper.selectUserById(user.getUserId())).getLoginName());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setUserType("01");
        user = encode(user);
        return userMapper.updateUser(user);
    }

    /**
     * 新增用户角色信息
     * 
     * @param user 用户对象
     */
    public void insertUserRole(User user)
    {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        for (Long roleId : user.getRoleIds())
        {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0)
        {
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 新增用户岗位信息
     * 
     * @param user 用户对象
     */
    public void insertUserPost(User user)
    {
        // 新增用户与岗位管理
        List<UserPost> list = new ArrayList<UserPost>();
        for (Long postId : user.getPostIds())
        {
            UserPost up = new UserPost();
            up.setUserId(user.getUserId());
            up.setPostId(postId);
            list.add(up);
        }
        if (list.size() > 0)
        {
            try {
            userPostMapper.batchUserPost(list);
            }catch (Exception e) {
                System.out.println("岗位重复。");
                return ;
            }
        }
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName)
    {
        try {
            //loginName = enc(encoder.encodeToString(loginName.getBytes("UTF-8")));
            loginName = enc(aescode.encrypt(loginName));
        } catch (Exception e) {
            return "0";
        }
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0 && !userMapper.selectUserByLoginName(loginName).getDelFlag().equals("2"))
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param phonenumber 用户名
     * @return
     */
    @Override
    public String checkPhoneUnique(User user)
    {
        user = encode(user);
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param email 用户名
     * @return
     */
    @Override
    public String checkEmailUnique(User user)
    {
        user = encode(user);
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param email 用户名
     * @return
     */
    @Override
    public String checkPassword(User user)
    {
        
        String pw = user.getPassword();
        if (matche(pw) >= 3)
        {
            return UserConstants.USER_EMAIL_UNIQUE;
        }
        return UserConstants.USER_EMAIL_NOT_UNIQUE;
    }
    
    public static int matche(String pw) {
        int level = 0;
        //if(pw.length()>8) {level++;} //长度>8
        if(pw.matches("(.*)[\\d](.*)")) {level++;} //有数字
        if(pw.matches("(.*)[a-zA-Z](.*)")) {level++;} //有字母
        if(pw.matches("(.*)[^\\da-zA-Z](.*)")) {level++;} //有特殊符号
        if(pw.matches("(.*)[&<>\"\'/\\?;:%=\\+\\s](.*)")) {level =0;}  //不可包含&<>"'/?;:%=+
        return level;
    }
    
    /**
     * 查询用户所属角色组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId)
    {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId)
    {
        List<Post> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Post post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }


    /**
     * 检查登陆IP是否被授权
     * @param ip
     * @return
     */
    @Override
    public boolean checkIpIsAuthenticate(String ip) {
        TrustHost trustHost = new TrustHost();
        trustHost.setIsValid("yes");
        List<TrustHost> trustHosts = trustHostService.selectTrustHostList(trustHost);

        if (ip == null)
            return false;

        //if (ip.trim().equals("127.0.0.1"))
            //return true;

        for (TrustHost trustHost1 : trustHosts) {
            if (ip.trim().equals(trustHost1.getIp().trim())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String selectUserType(String loginName) {
        User user;
        try {
        //user = userMapper.selectUserType(enc(encoder.encodeToString(loginName.getBytes("UTF-8"))));
        user = userMapper.selectUserType(aescode.encrypt(loginName));
        } catch (Exception e) {
            return "1";
        }
        
        return decode(user).getUserType();
    }
    
    public User decode(User user) {
        if (user == null)
            return null;
        try {
            if(user.getLoginName()!=null)
                user.setLoginName(aescode.decrypt(user.getLoginName()));
                //user.setLoginName(new String(decoder.decode(dec(user.getLoginName())),"UTF-8"));
            if(user.getUserName()!=null)
                user.setUserName(aescode.decrypt(user.getUserName()));
                //user.setUserName(new String(decoder.decode(dec(user.getUserName())),"UTF-8"));
            if(user.getEmail()!=null)
                user.setEmail(aescode.decrypt(user.getEmail()));
                //user.setEmail(new String(decoder.decode(dec(user.getEmail())),"UTF-8"));
            if(user.getPhonenumber()!=null)
                user.setPhonenumber(aescode.decrypt(user.getPhonenumber()));
                //user.setPhonenumber(new String(decoder.decode(dec(user.getPhonenumber())),"UTF-8"));
            if(user.getLoginIp()!=null)
                user.setLoginIp(aescode.decrypt(user.getLoginIp()));
                //user.setLoginIp(new String(decoder.decode(dec(user.getLoginIp())),"UTF-8"));
        } catch (Exception e) {
            System.out.println("用户信息解密失败。"+user);
            return null;
        }
        return user;
    }
    
    private String dec(String str) {
        while(str.length()%4 != 0) {str = str + "=";}
        return str;
    }
    private String enc(String str) {
        while(str.endsWith("=")) {str = str.substring(0, str.length()-1);}
        return str;
    }
    
    public User encode(User user) {
        try {
            if(user.getLoginName()!=null)
                user.setLoginName(aescode.encrypt(user.getLoginName()));
                //user.setLoginName(enc(encoder.encodeToString(user.getLoginName().getBytes("UTF-8"))));
            if(user.getUserName()!=null)
                user.setUserName(aescode.encrypt(user.getUserName()));
                //user.setUserName(enc(encoder.encodeToString(user.getUserName().getBytes("UTF-8"))));
            if(user.getEmail()!=null)
                user.setEmail(aescode.encrypt(user.getEmail()));
                //user.setEmail(enc(encoder.encodeToString(user.getEmail().getBytes("UTF-8"))));
            if(user.getPhonenumber()!=null)
                user.setPhonenumber(aescode.encrypt(user.getPhonenumber()));
                //user.setPhonenumber(enc(encoder.encodeToString(user.getPhonenumber().getBytes("UTF-8"))));
            if(user.getLoginIp()!=null)
                user.setLoginIp(aescode.encrypt(user.getLoginIp()));
                //user.setLoginIp(enc(encoder.encodeToString(user.getLoginIp().getBytes("UTF-8"))));
            System.out.println(user);
        } catch (Exception e) {
            System.out.println("用户信息解密失败。"+user);
            return null;
        }
        return user;
    }
    
    @Override
    public AjaxResult downloadF() {
        List<User> list = selectUserList(new User());
        String stdout = "";
        for(User user : list) {
            stdout += user.getUserId()+"\t";
            if(user.getLoginName().length() >= 8) stdout +=user.getLoginName()+"\t";
            else stdout +=user.getLoginName()+"\t\t";
            
            if(user.getUserName().length() >= 8) stdout +=user.getUserName()+"\t";
            else stdout +=user.getUserName()+"\t\t";
            
            if(user.getEmail().length() >= 16) stdout +=user.getEmail()+"\t";
            else if(user.getEmail().length() >= 8) stdout +=user.getEmail()+"\t\t";
            else stdout +=user.getEmail()+"\t\t\t";
            
            stdout += user.getPhonenumber()+"\t"+(user.getSex().equals("0")? "男":"女") +"\t";
            
            if(user.getLoginIp().length() >= 8) stdout +=user.getLoginIp()+"\t";
            else stdout +=user.getLoginIp()+"\t\t";

            stdout += user.getCreateTime()+"\n";
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "用户管理-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(stdout, ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
    
}

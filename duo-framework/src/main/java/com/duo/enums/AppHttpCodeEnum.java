package com.duo.enums;

/**
 * <pre>
 * 响应枚举
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 15:25
 */

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506,"内容不能为空"),
    FILE_TYPE_ERROR(507,"文件类型错误，需要上传PNG文件"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    TAG_EXIST(513, "标签已存在"),
    TAG_ADD_ERROR(514,"标签添加失败" ),
    TAG_NOT_FOUND(515, "标签不存在"),
    TAG_DELETE_ERROR(516,"标签删除失败" ),
    NAME_NOT_NULL(517, "标签名不能为空"),
    TAG_NAME_EXIST(518, "标签已经存在"),
    TAG_UPDATE_ERROR(519, "标签更新错误"),
    ARTICLE_NOT_EXIST(520,"文章不存在" ),
    MENU_EXIST(521,"菜单存在" ),
    ADMIN_ERROR(522,"不能删除管理员"),
    ROLE_EXIST(523, "角色已存在"),
    ROLE_NOT_EXIST(524, "角色不存在"),
    CAN_NOT_DELETE_ADMIN(525, "管理员不能删除"),
    USER_NOT_EXIST(526, "用户不存在"),
    CATEGORY_NOT_ESIXT(527, "分类不存在"),
    LINK_NOT_EXIST(528, "友链不存在");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

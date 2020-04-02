package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.UserInfo;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from users")
    public List<UserInfo> findAll();
}

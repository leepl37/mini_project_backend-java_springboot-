package chironsoft.test.domain.model.app;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppMapper {

	int selectSignCnt(int userKey);


}

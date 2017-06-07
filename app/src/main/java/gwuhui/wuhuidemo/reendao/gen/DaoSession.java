package gwuhui.wuhuidemo.reendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import wuhui.wuhuidemo.greendao.entity.UserTest;

import gwuhui.wuhuidemo.reendao.gen.UserTestDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userTestDaoConfig;

    private final UserTestDao userTestDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userTestDaoConfig = daoConfigMap.get(UserTestDao.class).clone();
        userTestDaoConfig.initIdentityScope(type);

        userTestDao = new UserTestDao(userTestDaoConfig, this);

        registerDao(UserTest.class, userTestDao);
    }
    
    public void clear() {
        userTestDaoConfig.clearIdentityScope();
    }

    public UserTestDao getUserTestDao() {
        return userTestDao;
    }

}

package xyz.erupt.job.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import xyz.erupt.auth.model.EruptMenu;
import xyz.erupt.core.dao.EruptDao;
import xyz.erupt.core.util.ProjectUtil;
import xyz.erupt.job.model.EruptJob;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author liyuepeng
 * @date 2019-07-15.
 */
@Service
@Order(10)
@Log
public class JobDataLoadService implements CommandLineRunner {

    @Autowired
    private EruptDao eruptDao;

    @Autowired
    private EruptJobService eruptJobService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        List<EruptJob> list = eruptDao.queryEntityList(EruptJob.class, "status = true", null);
        for (EruptJob job : list) {
            eruptJobService.modifyJob(job);
        }
        log.info("Erupt scheduler initialization complete");
        new ProjectUtil().projectStartLoaded("job", first -> {
            if (first) {
                EruptMenu eruptMenu = eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu("job", "任务管理", null, 1, 10, "fa fa-cubes", null), "code", "job");
                eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu("EruptJob", "任务维护", "/build/table/EruptJob",
                        1, 0, "fa fa-tasks", eruptMenu), "code", "EruptJob");
                eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu("EruptJobLog", "任务日志", "/build/table/EruptJobLog",
                        1, 10, "fa fa-file-text", eruptMenu), "code", "EruptJobLog");
            }
        });
    }

}
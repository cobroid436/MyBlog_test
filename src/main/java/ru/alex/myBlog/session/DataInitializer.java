package ru.alex.myBlog.session;

import java.time.LocalDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import ru.alex.myBlog.entity.UsersEntity;

@ApplicationScoped
public class DataInitializer {

    @Inject
    UsersFacade uf;
    @Inject
    RolesFacade rf;
    @Inject
    GroupuserFacade guf;

    public void execute(@Observes @Initialized(ApplicationScoped.class) Object event) {
        LocalDateTime сreateDate = LocalDateTime.now();
        if (uf.count() == 0) {

            rf.create("ADMIN");
            rf.create("MANAGER");
            rf.create("USER");
            rf.create("GUEST");

            UsersEntity adm = uf.create("admin", "ADMIN", "admin1ADMIN", "admin@company.org", сreateDate);
            UsersEntity mnr = uf.create("manager", "MANAGER", "manager1MANAGER", "manager@company.org", сreateDate);
            UsersEntity usr = uf.create("user", "USER", "user1USER", "user@company.org", сreateDate);
            UsersEntity gst = uf.create("guest", "GUEST", "guest1GUEST", "guest@company.org", сreateDate);

            guf.create(adm, rf.find("ADMIN"));
            guf.create(adm, rf.find("MANAGER"));
            guf.create(adm, rf.find("USER"));
            guf.create(mnr, rf.find("MANAGER"));
            guf.create(mnr, rf.find("USER"));
            guf.create(usr, rf.find("USER"));
            guf.create(gst, rf.find("GUEST"));
        }

    }

}

package sc.ejb.session.dto;

import sc.ejb.session.BaseSessionBean;
import sc.ejb.session.TestStatefulSessionBeanRemote;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
@LocalBean
public class VertragEntityDTO extends BaseSessionBean implements TestStatefulSessionBeanRemote {
    private List<KontofondsEntityDTO> kontoFonds;

    private String state;
    @Resource
    SessionContext ctx;

    public VertragEntityDTO() {
        init();
    }

    public VertragEntityDTO(VertragDTO vertrag) {
        init();
        KontofondsDTO dto1 = new KontofondsDTO();
        KontofondsDTO dto2 = new KontofondsDTO();

        ArrayList kfeList = new ArrayList();
        kfeList.add(new KontofondsEntityDTO(dto1));
        kfeList.add(new KontofondsEntityDTO(dto2));
        kontoFonds = kfeList;
    }

    VertragEntityDTO(VertragEntityDTO vertrag) {
        init();
    }

    private void init() {
        System.out.println("VertragEntityDTO inited");
    }



    @Lock(LockType.READ)
    public String getState() {
        return state;
    }

    @Lock(LockType.WRITE)
    @AccessTimeout(10000)
    public void setState(String newState) {
        state = newState;
    }

    @Asynchronous
    public void getDSTables(){

    }


    @PostConstruct
    public void initByeEJB() {
        System.out.println("VertragEntityDTO is being initialized...");

    }

    @PreDestroy
    public void destroyByeEJB() {
        System.out.println("VertragEntityDTO is being destroyed...");

    }
}

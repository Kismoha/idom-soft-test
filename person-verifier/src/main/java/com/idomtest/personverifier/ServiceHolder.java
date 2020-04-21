package com.idomtest.personverifier;

import com.idomtest.resources.DocumentVerifierService;
import org.springframework.context.ConfigurableApplicationContext;

public class ServiceHolder {
    private static ServiceHolder instance;
    private ConfigurableApplicationContext context;
    private DocumentVerifierService documentVerifierService;

    public static ServiceHolder getInstance(){
        if(instance == null){
            instance = new ServiceHolder();
        }
        return instance;
    }

    public void loadContext(ConfigurableApplicationContext context){
        instance.context = context;
        loadServices();
    }

    private void loadServices(){
        instance.documentVerifierService = instance.context.getBean(DocumentVerifierService.class);
    }

    public DocumentVerifierService getDocumentVerifierService() {
        return instance.documentVerifierService;
    }
}

package be.ugent.flash.QuestionManager;


import be.ugent.flash.jdbc.DataAccesProvider;

import java.util.Map;

public class QuestionManager {
    private DataAccesProvider dataAccesProvider;

    public QuestionManager(DataAccesProvider dataAccesProvider) {
        this.dataAccesProvider=dataAccesProvider;
    }
    
    
    Map<String, QuestionFactory> factories= Map.of("mcs",new McsFactory(),"mcc",new MccFactory(),
            "mci",new MciFactory(),"mr",new MrFactory(),"open",new OpenFactory(),"openi",new OpenIFactory());


    public void run() {
    }
}

package edu.asu.heal.reachv3.api.models.moduleProgession;

public class ModuleBasedInstance {

    private String module;
    private String informationContent;
    private boolean isActive;

    public ModuleBasedInstance() {
        module =null;
        informationContent=null;
        isActive=false;
    }

    public ModuleBasedInstance(String module, String informationContent, boolean isActive) {
        this.module = module;
        this.informationContent = informationContent;
        this.isActive = isActive;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getInformationContent() {
        return informationContent;
    }

    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}

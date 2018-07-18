package engine;

import models.ApplicationSystem.EthicsApplication;

public class Filter {

    public enum RiskLevel {
        None,
        Faculty,
        RECCommittee
    }

    private EthicsApplication.ApplicationType type;
    private RiskLevel riskLevel;

    public Filter() {
    }

    public Filter(EthicsApplication.ApplicationType type, RiskLevel riskLevel) {
        this.type = type;
        this.riskLevel = riskLevel;
    }

    public EthicsApplication.ApplicationType getType() {
        return type;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }
}

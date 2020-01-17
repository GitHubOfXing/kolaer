package com.uxin.component_service;

class CallPowerBinder implements CallPower {

    private final CoreService core;
    private final Object[] args;

    CallPowerBinder(CoreService coreService, Object[] args) {
        this.core = coreService;
        this.args = args;
    }

    @Override
    public Object call() {
        return core.coreBuilt(args);
    }
}

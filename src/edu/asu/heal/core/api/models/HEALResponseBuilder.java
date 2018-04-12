package edu.asu.heal.core.api.models;

public class HEALResponseBuilder {
    private HEALResponse1 _response;

    public HEALResponseBuilder(Class modelClass) throws InstantiationException, IllegalAccessException {
        _response = (HEALResponse1) modelClass.newInstance();
    }

    public HEALResponseBuilder setStatusCode(int statusCode) {
        this._response.setStatusCode(statusCode);
        return this;
    }

    public HEALResponseBuilder setData(Object data) {
        this._response.setData(data);
        return this;
    }

    public HEALResponseBuilder setServerURI(String serverURI) {
        this._response.setServerURI(serverURI);
        return this;
    }

    public HEALResponse1 build() {
        return this._response;
    }
}

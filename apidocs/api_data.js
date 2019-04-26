define({ "api": [
  {
    "type": "get",
    "url": "/activities/:activityId",
    "title": "Get an Activity for an activity Id",
    "name": "ActivityDetail",
    "group": "Activity",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Activity's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activities/5a9496ef66684905df624348"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityResource.java",
    "groupTitle": "Activity",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>ActivityInstance(s) cannot be found</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/activities",
    "title": "Create an Activity",
    "name": "CreateActivity",
    "group": "Activity",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Title",
            "description": "<p>Title of the Activity</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Description",
            "description": "<p>Description of the Activity</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Activity Example:",
          "content": "{\n\"title\" : \"SWAP\",\n\"description\" : \"SWAP Activity\"\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activities"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityResource.java",
    "groupTitle": "Activity",
    "error": {
      "fields": {
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          }
        ],
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ]
      }
    }
  },
  {
    "type": "delete",
    "url": "/activities/:id",
    "title": "Delete an Activity",
    "name": "DeleteActivity",
    "group": "Activity",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Activity's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activities/5a9496ef66684905df624348"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityResource.java",
    "groupTitle": "Activity",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>ActivityInstance(s) cannot be found</p>"
          },
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/activities?domain={domainName}",
    "title": "Get list of Activities for a given domain",
    "name": "GetActivitiesByDomain",
    "group": "Activity",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "domain",
            "description": "<p>Domain name for which activities are to be fetched.</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activities?domain=Preventive Anxiety"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityResource.java",
    "groupTitle": "Activity",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>Activity cannot be found</p>"
          },
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/activities/suggestions?patientPin={patientPin}&emotion={emotionName}&intensity={intensityValue}",
    "title": "Get suggested activities based on the emotionName and intensity level",
    "name": "GetSuggestedActivities",
    "group": "Activity",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "patientPin",
            "description": "<p>Patient's Unique Id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "emotion",
            "description": "<p>Name of the emotion</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "intensity",
            "description": "<p>Intensity level of the emotion</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activities/suggestions?patientPin=4015&emotion=sad&intensity=5"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityResource.java",
    "groupTitle": "Activity",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          },
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>ActivityInstance(s) cannot be found</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/activityinstances/:id",
    "title": "Get Activity Instances for an activityInstanceId",
    "name": "ActivityInstanceDetail",
    "group": "ActivityInstance",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ActivityInstance's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activityinstances/5c5b901a324b051370ac2f3e"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",
    "groupTitle": "ActivityInstance",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          },
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>ActivityInstance(s) cannot be found</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/activityinstances",
    "title": "Create an ActivityInstance",
    "name": "CreateActivityInstance",
    "group": "ActivityInstance",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Description",
            "description": "<p>Description about the Activity Instance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "StartTime",
            "description": "<p>Start Time of the Activity Instance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "EndTime",
            "description": "<p>End Time of the Activity Instance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "UserSubmissionTime",
            "description": "<p>User Submission Time of the ActivityInstance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "ActualSubmissionTime",
            "description": "<p>Actual Submission Time of the ActivityInstance</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "patientPin",
            "description": "<p>Patient's Unique Id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Name",
            "description": "<p>The name of the activity</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Activity Instance Example:",
          "content": "{\n  \"description\": \"SWAP activity\",\n  \"startTime\": \"2019-02-07T01:05:25.286Z\",\n  \"endTime\": null,\n  \"userSubmissionTime\": null,\n  \"actualSubmissionTime\": null,\n  \"instanceOf\": {\n      \"name\": \"SWAP\"\n   },\n  \"patientPin\": 4015\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activityinstances"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",
    "groupTitle": "ActivityInstance",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "delete",
    "url": "/activityinstances/:id",
    "title": "Delete an ActivityInstance",
    "name": "DeleteActivityInstance",
    "group": "ActivityInstance",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ActivityInstance's unique id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activityinstances/5c5b901a324b051370ac2f3e"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",
    "groupTitle": "ActivityInstance",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          },
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>ActivityInstance(s) cannot be found</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/activityinstances?patientPin={patientPin}",
    "title": "Get Activity Instances for a patient",
    "name": "GetActivityInstancesOfPatient",
    "group": "ActivityInstance",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "patientPin",
            "description": "<p>Patient's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activityinstances?patientPin=4015"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",
    "groupTitle": "ActivityInstance",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          },
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>ActivityInstance(s) cannot be found</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/activityinstances/:activityInstanceId",
    "title": "Update an ActivityInstance",
    "name": "UpdateActivityInstance",
    "group": "ActivityInstance",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ActivityInstanceId",
            "description": "<p>ActivityInstance's Unique Id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Description",
            "description": "<p>Description about the Activity Instance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "createdAt",
            "description": "<p>Created Date and Time of the Activity Instance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "updatedAt",
            "description": "<p>Updated Data and Time of the Activity Instance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "StartTime",
            "description": "<p>Start Time of the Activity Instance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "EndTime",
            "description": "<p>End Time of the Activity Instance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "UserSubmissionTime",
            "description": "<p>User Submission Time of the ActivityInstance</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "ActualSubmissionTime",
            "description": "<p>Actual Submission Time of the ActivityInstance</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "patientPin",
            "description": "<p>Patient's Unique Id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Name",
            "description": "<p>The name of the activity</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "state",
            "description": "<p>The status of the Activity Instance from Created | Available | In Execution (Running) | Suspended | Completed | Aborted</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Activity Instance Example:",
          "content": "{\n        \"activityInstanceId\": \"5c5b901a324b051370ac2f3e\",\n        \"createdAt\": \"2019-02-07T01:30:34.947Z\",\n        \"updatedAt\": \"2019-02-07T01:30:34.947Z\",\n        \"description\": \"SWAP activity\",\n        \"startTime\": \"2019-02-07T01:05:25.286Z\",\n        \"endTime\": null,\n        \"userSubmissionTime\": null,\n        \"actualSubmissionTime\": null,\n        \"instanceOf\": {\n            \"name\": \"SWAP\",\n        },\n        \"state\": \"completed\",\n        \"patientPin\": 4015,\n        \"situation\": \"Explain the principal\",\n        \"worry\": \"Fear to speak\",\n        \"action\": \"Practice WorryHeads\"\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activityinstances/5c5b901a324b051370ac2f3e"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityInstanceResource.java",
    "groupTitle": "ActivityInstance",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/activities",
    "title": "Update an Activity",
    "name": "UpdateActivity",
    "group": "Activity",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Title",
            "description": "<p>Title of the Activity</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Description",
            "description": "<p>Description of the Activity</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ActivityId",
            "description": "<p>Unique Id of an Activity</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "createdAt",
            "description": "<p>Created Date and Time of the Activity</p>"
          },
          {
            "group": "Parameter",
            "type": "DateTime",
            "optional": false,
            "field": "updatedAt",
            "description": "<p>Updated Data and Time of the Activity</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Activity Example:",
          "content": "{\n\"activityId\" : \"5a9496ef66684905df624348\",\n\"title\" : \"SWAP\",\n\"description\" : \"SWAP Activity\",\n\"createdAt\" : ISODate(\"2018-02-26T07:00:00Z\"),\n\"updatedAt\" : ISODate(\"2018-02-26T07:00:00Z\")\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/activities"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ActivityResource.java",
    "groupTitle": "Activity",
    "error": {
      "fields": {
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          }
        ],
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/domain",
    "title": "Create Domain",
    "name": "CreateDomain",
    "group": "Domain",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Title",
            "description": "<p>Title of the Domain</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Description",
            "description": "<p>Description of the Domain</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "State",
            "description": "<p>The status of the Domain from Active | InActive</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Activity Example:",
          "content": "{\n \t \"title\": \"COMPASS\",\n      \"description\": \"Heal domain for preventive anxiety applicatons for children\",\n      \"state\": \"Active\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/DomainResource.java",
    "groupTitle": "Domain",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "DomainNotFoundError",
            "description": "<p>Domain Not Found!</p>"
          },
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/domains/:id",
    "title": "Get a specific domain Detail",
    "name": "DomainDetail",
    "group": "Domain",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Domain's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/domains/5a937d15f85cf71f59e36411"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/DomainResource.java",
    "groupTitle": "Domain",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/domains",
    "title": "Get list of all Domains",
    "name": "GetDomains",
    "group": "Domain",
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/domains"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/DomainResource.java",
    "groupTitle": "Domain",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "DomainNotFoundError",
            "description": "<p>Domain Not Found!</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/logger",
    "title": "Add Logs",
    "name": "AddLogs",
    "group": "Logger",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Array",
            "optional": false,
            "field": "loggerJSON",
            "description": "<p>array of logs in JSON format</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/LoggerResource.java",
    "groupTitle": "Logger",
    "error": {
      "fields": {
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/patient",
    "title": "Add Patient",
    "name": "AddPatient",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "Trial",
            "description": "<p>ID of the trial to which the patient needs to be added</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",
    "groupTitle": "Patient",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/patient/:id",
    "title": "Get detail for a specific Patient",
    "name": "GetPatientDetail",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>Patient's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/patients/4014"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",
    "groupTitle": "Patient",
    "error": {
      "fields": {
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          }
        ],
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>Patient could not be found</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/patient/:id",
    "title": "Get detail for a specific Patient",
    "name": "GetPatientDetail",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>Patient's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/patients/4014"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",
    "groupTitle": "Patient",
    "error": {
      "fields": {
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          }
        ],
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>Patient could not be found</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/patients?trialId={trialId}",
    "title": "Get list of all Patients",
    "name": "GetPatients",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "trialId",
            "description": "<p>Pass trialId = 'some-unique-id' as query parameter to fetch the list of patients for a particular trial; eg: <code>/patient?trialId=1</code></p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",
    "groupTitle": "Patient",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>Patient could not be found</p>"
          }
        ]
      }
    }
  },
  {
    "type": "put",
    "url": "/patients",
    "title": "Update Patient",
    "name": "updatePatients",
    "group": "Patient",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "Patient",
            "description": "<p>JSON structure</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-payload :",
          "content": "{\n\"pin\": 4010,\n\"startDate\": \"2018-01-01 13:00:00\",\n\"endDate\": \"2018-03-01 13:00:00\",\n\"state\": \"completed\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/PatientResource.java",
    "groupTitle": "Patient",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "/trials",
    "title": "Create Trial",
    "name": "CreateTrial",
    "group": "Trial",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "domainId",
            "description": "<p>DomainId for which the trial is being created</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>Title of the Trial</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "<p>Description of the Trial</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "startDate",
            "description": "<p>Start Date for the Trial</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "endDate",
            "description": "<p>End Date for the Trial</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "targetCount",
            "description": "<p>Target Count of the Trial</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Activity Example:",
          "content": "{\n     domainId=5abd64f5734d1d0cf303bda1\n     title=\"Compass\"\n     description=\"Compass for Courage\"\n     startDate=\"2018-02-26T07:00:00Z\"\n     endDate=\"2018-04-25T07:00:00Z\"\n     targetCount=100\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/TrialsResource.java",
    "groupTitle": "Trial",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          },
          {
            "group": "Error 4xx",
            "type": "404",
            "optional": false,
            "field": "NotFound",
            "description": "<p>Trial cannot be found</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "/trials?domain={domainName}",
    "title": "Get list of trials for a given domain",
    "name": "getTrials",
    "group": "Trials",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "domain",
            "description": "<p>Domain name for which trials are to be fetched.</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/ReachAPI/rest/trials?domain=Preventive Anxiety"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/TrialsResource.java",
    "groupTitle": "Trials",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "400",
            "optional": false,
            "field": "BadRequest",
            "description": "<p>Bad Request Encountered</p>"
          }
        ],
        "Error 5xx": [
          {
            "group": "Error 5xx",
            "type": "500",
            "optional": false,
            "field": "InternalServerError",
            "description": "<p>Something went wrong at server, Please contact the administrator!</p>"
          },
          {
            "group": "Error 5xx",
            "type": "501",
            "optional": false,
            "field": "NotImplemented",
            "description": "<p>The resource has not been implemented. Please keep patience, our developers are working hard on it!!</p>"
          }
        ]
      }
    }
  }
] });

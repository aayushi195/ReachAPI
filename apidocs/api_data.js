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
            "field": "activityId",
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
          "content": "{\n    \"title\" : \"SWAP\",\n    \"description\" : \"SWAP Activity\"\n}",
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
    "url": "/activities/:activityId",
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
            "field": "activityId",
            "description": "<p>Activity's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/activities/5a9496ef66684905df624348"
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
        "url": "http://localhost:8080/CompassAPI/rest/activities?domain=Preventive Anxiety"
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
    "title": "Get suggested activities based on the emotion name and intensity level",
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
    "url": "/activityinstances/:activityInstanceId",
    "title": "Get Activity Instances for an activity Instance Id",
    "name": "ActivityInstanceDetail",
    "group": "ActivityInstance",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "activityInstanceId",
            "description": "<p>ActivityInstance's Unique Id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/activityinstances/5c5b901a324b051370ac2f3e"
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
            "field": "ActivityId",
            "description": "<p>Unique id of the activity</p>"
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
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Payload:",
          "content": "{\n\t\"activityId\": \"5a9496ef66684905df624348\",\n\t\"description\": \"SWAP activity\",\n\t\"startTime\": \"2018-10-23T07:00:00.000Z\",\n\t\"endTime\": null,\n\t\"userSubmissionTime\": null,\n\t\"actualSubmissionTime\": null,\n\t\"patientPin\": 4011\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/activityinstances"
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
    "url": "/activityinstances/:activityInstanceId",
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
            "field": "activityInstanceId",
            "description": "<p>ActivityInstance's unique id</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/activityinstances/5c5b901a324b051370ac2f3e"
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
        "url": "http://localhost:8080/CompassAPI/rest/activityinstances?patientPin=4010"
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
            "field": "ActivityId",
            "description": "<p>Unique id of the activity</p>"
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
            "field": "state",
            "description": "<p>The status of the Activity Instance from Created | In Progress (Running) | Completed</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Payload:",
          "content": "{\n\t\"activityInstanceId\": \"5cca73cf89f38e3d343fdcae\",\n\t\"activityId\": \"5a9496ef66684905df624348\",\n\t\"createdAt\": 1556771790775,\n\t\"updatedAt\": 1556771790776,\n\t\"description\": \"SWAP activity\",\n\t\"startTime\": 1540278000000,\n\t\"endTime\": null,\n\t\"userSubmissionTime\": null,\n\t\"actualSubmissionTime\": null,\n\t\"state\": \"created\",\n\t\"patientPin\": 4012,\n\t\"extended\": {\n\t\t\"domainName\": \"Preventive Anxiety\",\n\t\t\"activityTypeName\": \"SWAP\",\n\t\t\"version\": \"v1\",\n\t\t\"situation\": {\n\t\t\t\"situationId\": 1,\n\t\t\t\"situationTitle\": \"SWAP Situation\",\n\t\t\t\"questions\": [\n\t\t\t\t{\n\t\t\t\t\t\"questionId\": 1,\n\t\t\t\t\t\"questionText\": \"SITUATION (NOTICE THE SITUATION THAT MAKES YOU WORRY, SCARED OR NERVOUS)\",\n\t\t\t\t\t\"answerText\": null\n\t\t\t\t},\n\t\t\t\t{\n\t\t\t\t\t\"questionId\": 2,\n\t\t\t\t\t\"questionText\": \"WORRY (NOTICE THE WORRY THAT POPS INTO YOUR HEAD)\",\n\t\t\t\t\t\"answerText\": null\n\t\t\t\t},\n\t\t\t\t{\n\t\t\t\t\t\"questionId\": 3,\n\t\t\t\t\t\"questionText\": \"ACTION THOUGHT OR PLAN (WHAT ELSE CAN HAPPEN? HOW CAN YOU SOLVE THAT?)\",\n\t\t\t\t\t\"answerText\": null\n\t\t\t\t},\n\t\t\t\t{\n\t\t\t\t\t\"questionId\": 4,\n\t\t\t\t\t\"questionText\": \"PRACTICE (ACTION THOUGHT OR PLAN)\",\n\t\t\t\t\t\"answerText\": null\n\t\t\t\t}\n\t\t\t]\n\t\t}\n\t}\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/activityinstances/5c5b901a324b051370ac2f3e"
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
          "content": "{\n        \"activityId\": \"5a9496ef66684905df624348\",\n        \"createdAt\": \"2019-05-02T05:29:33.207Z[UTC]\",\n        \"description\": \"SWAP Activity\",\n        \"title\": \"SWAP\",\n        \"updatedAt\": \"2019-05-02T05:29:33.207Z[UTC]\"\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/activities"
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
    "url": "/domains",
    "title": "Create Domain",
    "name": "CreateDomain",
    "group": "Domain",
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/domains"
      }
    ],
    "parameter": {
      "examples": [
        {
          "title": "Request-Payload:",
          "content": "{\n\"title\": \"Preventive Anxiety\",\n\"description\": \"Heal domain for preventive anxiety applicatons\",\n\"state\": \"Active\"\n}",
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
    "title": "Get a specific domain",
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
        "url": "http://localhost:8080/CompassAPI/rest/domains/5abd64f5734d1d0cf303bda1"
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
        "url": "http://localhost:8080/CompassAPI/rest/domains"
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
    "title": "Create Logs",
    "name": "AddLogs",
    "group": "Logger",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "array",
            "optional": false,
            "field": "loggerJSON",
            "description": "<p>array of logs in JSON format</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Payload:",
          "content": "[\n{\n\"format\" : \"JSON\",\n\"level\" : \"INFO\",\n\"metadata\" : \"{\\\"buttonName\\\":\\\"pressBtn\\\",\\\"methodName\\\":\\\"onClick\\\"}\",\n\"subtype\" : \"Settings\",\n\"timeStamp\" : \"19.01.2019 14:46:50\",\n\"trialId\" : \"5a946ff566684905df608446\",\n\"type\" : \"UI_EVENT\"\n}\n]",
          "type": "array"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/logger"
      }
    ],
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
    "type": "get",
    "url": "/modules/:id?patientPin={patientPin}",
    "title": "Get list of activities for a given module for a specific patient",
    "name": "ModuleActivityDetail",
    "group": "Modules",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>Module Number</p>"
          },
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
        "url": "http://localhost:8080/CompassAPI/rest/modules/1?patientPin=4011"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ModuleResource.java",
    "groupTitle": "Modules",
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
            "description": "<p>Patient could not be found</p>"
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
    "url": "/modules/progression?patientPin={patientPin}",
    "title": "Get progression for a specific Patient",
    "name": "ModuleProgressionDetail",
    "group": "Modules",
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
        "url": "http://localhost:8080/CompassAPI/rest/modules/progression?patientPin=4011"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ModuleResource.java",
    "groupTitle": "Modules",
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
            "description": "<p>Patient could not be found</p>"
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
    "url": "/patients",
    "title": "Create Patient",
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
      },
      "examples": [
        {
          "title": "Request-Payload:",
          "content": "5a946ff566684905df608446",
          "type": "text"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/patients"
      }
    ],
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
    "url": "/patient/:patientPin",
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
            "field": "patientPin",
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
    "url": "/patient/:patientPin/rewards",
    "title": "Get rewards for a specific Patient",
    "name": "GetPatientRewards",
    "group": "Patient",
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
        "url": "http://localhost:8080/ReachAPI/rest/patients/4014/rewards"
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
            "optional": false,
            "field": "trialId",
            "description": "<p>Unique Id of a trial</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/patients?trialId=5a946ff566684905df608446"
      }
    ],
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
          "content": "{\n\"pin\": 4010,\n\"startDate\": \"2018-10-23T07:00:00.000Z,\n\"endDate\": \"2018-10-23T07:00:00.000Z,\n\"state\": \"Active\"\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/patients"
      }
    ],
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
    "url": "/schedules",
    "title": "Create Schedule of a patient",
    "name": "CreateSchedule",
    "group": "Schedules",
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
      },
      "examples": [
        {
          "title": "Request-Payload:",
          "content": "{\n\t\t\"patientPin\" : 4012\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/schedules"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ScheduleResource.java",
    "groupTitle": "Schedules",
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
            "description": "<p>Schedule cannot be found</p>"
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
    "url": "/schedules/groups",
    "title": "Create Schedule of a patient",
    "name": "CreateSchedule",
    "group": "Schedules",
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
      },
      "examples": [
        {
          "title": "Request-Payload:",
          "content": "{\n\t\t\"patientPin\" : 4012\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/schedules"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ScheduleResource.java",
    "groupTitle": "Schedules",
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
            "description": "<p>Schedule cannot be found</p>"
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
    "url": "/schedules?patientPin={patientPin}",
    "title": "Get schedule for a specific Patient",
    "name": "PatientScheduleDetail",
    "group": "Schedules",
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
        "url": "http://localhost:8080/CompassAPI/rest/schedules?patientPin=4010"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ScheduleResource.java",
    "groupTitle": "Schedules",
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
            "description": "<p>Schedule cannot be found</p>"
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
    "type": "patch",
    "url": "/schedules",
    "title": "Update schedule of the patient",
    "name": "UpdateSchedule",
    "group": "Schedules",
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
            "field": "module",
            "description": "<p>Module Number</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Payload:",
          "content": "{\n\t\t\"patientPin\" : 4012\n\t\t\"module\" : \"3\"\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/schedules"
      }
    ],
    "version": "0.0.0",
    "filename": "src/edu/asu/heal/core/api/resources/ScheduleResource.java",
    "groupTitle": "Schedules",
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
            "description": "<p>Schedule cannot be found</p>"
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
    "group": "Trials",
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
          "title": "Request-Payload:",
          "content": "{\n  \"domainId\":\"5abd64f5734d1d0cf303bda1\",\n  \"title\":\"SCD\",\n  \"description\":\"Sickle Cell Disease\",\n  \"startDate\":\"2018-10-23T07:00:00.000Z\",\n  \"endDate\":\"2019-10-23T07:00:00.000Z\",\n  \"targetCount\":100\n}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "http://localhost:8080/CompassAPI/rest/trials"
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
        "url": "http://localhost:8080/CompassAPI/rest/trials?domain=Preventive Anxiety"
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
  }
] });

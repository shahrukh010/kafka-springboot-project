cube(`Customers`, {
  sql: `SELECT * FROM shopmedb.customers`,
  
  preAggregations: {
    // Pre-Aggregations definitions go here
    // Learn more here: https://cube.dev/docs/caching/pre-aggregations/getting-started  
  },
  
  joins: {
    Countries: {
      sql: `${CUBE}.country_id = ${Countries}.id`,
      relationship: `belongsTo`
    }
  },
  
  measures: {
    count: {
      type: `count`,
      drillMembers: [city, firstName, id, lastName, createdTime]
    }
  },
  
  dimensions: {
    addressLine1: {
      sql: `address_line1`,
      type: `string`
    },
    
    addressLine2: {
      sql: `address_line2`,
      type: `string`
    },
    
    authenticationType: {
      sql: `authentication_type`,
      type: `string`
    },
    
    city: {
      sql: `city`,
      type: `string`
    },
    
    email: {
      sql: `email`,
      type: `string`
    },
    
    enabled: {
      sql: `enabled`,
      type: `string`
    },
    
    firstName: {
      sql: `first_name`,
      type: `string`
    },
    
    id: {
      sql: `id`,
      type: `number`,
      primaryKey: true
    },
    
    lastName: {
      sql: `last_name`,
      type: `string`
    },
    
    password: {
      sql: `password`,
      type: `string`
    },
    
    phoneNumber: {
      sql: `phone_number`,
      type: `string`
    },
    
    postalCode: {
      sql: `postal_code`,
      type: `string`
    },
    
    state: {
      sql: `state`,
      type: `string`
    },
    
    verificationCode: {
      sql: `verification_code`,
      type: `string`
    },
    
    createdTime: {
      sql: `created_time`,
      type: `time`
    }
  },
  
  dataSource: `default`
});

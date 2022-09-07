cube(`Products`, {
  sql: `SELECT * FROM shopmedb.products`,
  
  preAggregations: {
    // Pre-Aggregations definitions go here
    // Learn more here: https://cube.dev/docs/caching/pre-aggregations/getting-started  
  },
  
  joins: {
    Brands: {
      sql: `${CUBE}.brand_id = ${Brands}.id`,
      relationship: `belongsTo`
    },
    
    Categories: {
      sql: `${CUBE}.categories_id = ${Categories}.id`,
      relationship: `belongsTo`
    }
  },
  
  measures: {
    count: {
      type: `count`,
      drillMembers: [id, name, width, createdTime, updatedTime]
    },
    
    reviewCount: {
      sql: `review_count`,
      type: `sum`
    }
  },
  
  dimensions: {
    alias: {
      sql: `alias`,
      type: `string`
    },
    
    averageRating: {
      sql: `average_rating`,
      type: `string`
    },
    
    cost: {
      sql: `cost`,
      type: `string`
    },
    
    discountPrice: {
      sql: `discount_price`,
      type: `string`
    },
    
    enabled: {
      sql: `enabled`,
      type: `string`
    },
    
    fullDescription: {
      sql: `full_description`,
      type: `string`
    },
    
    height: {
      sql: `height`,
      type: `string`
    },
    
    id: {
      sql: `id`,
      type: `number`,
      primaryKey: true
    },
    
    inStock: {
      sql: `in_stock`,
      type: `string`
    },
    
    length: {
      sql: `length`,
      type: `string`
    },
    
    mainImage: {
      sql: `main_image`,
      type: `string`
    },
    
    name: {
      sql: `name`,
      type: `string`
    },
    
    price: {
      sql: `price`,
      type: `string`
    },
    
    shortDescription: {
      sql: `short_description`,
      type: `string`
    },
    
    weight: {
      sql: `weight`,
      type: `string`
    },
    
    width: {
      sql: `width`,
      type: `string`
    },
    
    createdTime: {
      sql: `created_time`,
      type: `time`
    },
    
    updatedTime: {
      sql: `updated_time`,
      type: `time`
    }
  },
  
  dataSource: `default`
});

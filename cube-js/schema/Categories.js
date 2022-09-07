cube(`Categories`, {
  sql: `SELECT * FROM shopmedb.categories`,
  
  preAggregations: {
    // Pre-Aggregations definitions go here
    // Learn more here: https://cube.dev/docs/caching/pre-aggregations/getting-started  
  },
  
  joins: {
    
  },
  
  measures: {
    count: {
      type: `count`,
      drillMembers: [allParentId, id, name]
    }
  },
  
  dimensions: {
    alias: {
      sql: `alias`,
      type: `string`
    },
    
    allParentId: {
      sql: `all_parent_id`,
      type: `string`
    },
    
    enabled: {
      sql: `enabled`,
      type: `string`
    },
    
    id: {
      sql: `id`,
      type: `number`,
      primaryKey: true
    },
    
    images: {
      sql: `images`,
      type: `string`
    },
    
    name: {
      sql: `name`,
      type: `string`
    }
  },
  
  dataSource: `default`
});

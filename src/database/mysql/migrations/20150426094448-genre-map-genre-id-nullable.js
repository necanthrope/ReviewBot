var dbm = global.dbm || require('db-migrate');
var type = dbm.dataType;

exports.up = function(db, callback) {
  db.runSql("alter table genre_map modify genre_id int(11)",callback);
};

exports.down = function(db, callback) {
  db.runSql("alter table genre_map modify genre_id int(11) not null;",callback);
};

-- date & time
CREATE ALIAS UNIX_TIMESTAMP FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.unixTimestamp";
CREATE ALIAS FROM_UNIXTIME FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.fromUnixTime";
CREATE ALIAS ADDDATE FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.addDate";
CREATE ALIAS ADDTIME FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.addTime";
CREATE ALIAS DATE FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.date";
CREATE ALIAS TIME FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.time";
CREATE ALIAS UTC_TIMESTAMP FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.utcTimestamp";
CREATE ALIAS UTC_DATE FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.utcDate";
CREATE ALIAS UTC_TIME FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.utcTime";
CREATE ALIAS FROM_DAYS FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.fromDays";
CREATE ALIAS TO_DAYS FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.toDays";
CREATE ALIAS TO_SECONDS FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.toSeconds";
CREATE ALIAS TIME_TO_SEC FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.timeToSeconds";
CREATE ALIAS DATE_FORMAT FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.dateFormat";
CREATE ALIAS TIME_FORMAT FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.timeFormat";
CREATE ALIAS LAST_DAY FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.lastDay";
CREATE ALIAS MAKEDATE FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.makeDate";
CREATE ALIAS MAKETIME FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.makeTime";
CREATE ALIAS SEC_TO_TIME FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.secondsToTime";
CREATE ALIAS SLEEP FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.sleep";
CREATE ALIAS STR_TO_DATE FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.strToDate";
CREATE ALIAS SUBDATE FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.subDate";
CREATE ALIAS SUBTIME FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.subTime";
CREATE ALIAS YEARWEEK FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.yearWeek";
CREATE ALIAS WEEKOFYEAR FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.weekOfYear";
CREATE ALIAS WEEKDAY FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.weekDay";
CREATE ALIAS MICROSECOND FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.microSecond";
CREATE ALIAS CONVERT_TZ FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.convertTZ";
CREATE ALIAS PERIOD_ADD FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.periodAdd";
CREATE ALIAS PERIOD_DIFF FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.periodDiff";
CREATE ALIAS TIMEDIFF FOR "com.boilerplate.demo.h2.function.DateTimeFunctions.timeDiff";
-- encrypt
CREATE ALIAS MD5 FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.md5";
CREATE ALIAS SHA1 FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.sha1";
CREATE ALIAS SHA FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.sha";
CREATE ALIAS HEX FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.hex";
CREATE ALIAS UNHEX FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.unhex";
CREATE ALIAS PASSWORD FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.password";
CREATE ALIAS TO_BASE64 FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.base64";
CREATE ALIAS FROM_BASE64 FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.unbase64";
CREATE ALIAS RANDOM_BYTES FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.randomBytes";
CREATE ALIAS AES_ENCRYPT FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.aesEncrypt";
CREATE ALIAS AES_DECRYPT FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.aesDecrypt";
CREATE ALIAS CRC32 FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.crc32";
CREATE ALIAS CREATE_DIGEST FOR "com.boilerplate.demo.h2.function.EncryptionFunctions.createDigest";
-- string functions
CREATE ALIAS BIN FOR "com.boilerplate.demo.h2.function.StringFunctions.bin";
CREATE ALIAS UUID_SHORT FOR "com.boilerplate.demo.h2.function.StringFunctions.uuidShort";
CREATE ALIAS FIND_IN_SET FOR "com.boilerplate.demo.h2.function.StringFunctions.findInSet";
CREATE ALIAS IS_IPV4 FOR "com.boilerplate.demo.h2.function.StringFunctions.isIpv4";
CREATE ALIAS IS_IPV6 FOR "com.boilerplate.demo.h2.function.StringFunctions.isIpv6";
CREATE ALIAS IS_UUID FOR "com.boilerplate.demo.h2.function.StringFunctions.isUUID";
CREATE ALIAS REVERSE FOR "com.boilerplate.demo.h2.function.StringFunctions.reverse";
CREATE ALIAS SUBSTRING_INDEX FOR "com.boilerplate.demo.h2.function.StringFunctions.subStringIndex";
CREATE ALIAS STRCMP FOR "com.boilerplate.demo.h2.function.StringFunctions.strCmp";
CREATE ALIAS CHARSET FOR "com.boilerplate.demo.h2.function.StringFunctions.charset";
CREATE ALIAS FIELD FOR "com.boilerplate.demo.h2.function.StringFunctions.field";
CREATE ALIAS MID FOR "com.boilerplate.demo.h2.function.StringFunctions.mid";
CREATE ALIAS ORD FOR "com.boilerplate.demo.h2.function.StringFunctions.ord";
CREATE ALIAS QUOTE FOR "com.boilerplate.demo.h2.function.StringFunctions.quote";
-- math functions
CREATE ALIAS POW FOR "com.boilerplate.demo.h2.function.MathFunctions.pow";
CREATE ALIAS CONV FOR "com.boilerplate.demo.h2.function.MathFunctions.conv";
-- misc
CREATE ALIAS FORMAT FOR "com.boilerplate.demo.h2.function.MiscFunctions.format";
CREATE ALIAS VERSION FOR "com.boilerplate.demo.h2.function.MiscFunctions.version";
CREATE ALIAS CONNECTION_ID FOR "com.boilerplate.demo.h2.function.MiscFunctions.connectId";
CREATE ALIAS SESSION_USER FOR "com.boilerplate.demo.h2.function.MiscFunctions.sessionUser";
CREATE ALIAS SYSTEM_USER FOR "com.boilerplate.demo.h2.function.MiscFunctions.sessionUser";
CREATE ALIAS CURRENT_ROLE FOR "com.boilerplate.demo.h2.function.MiscFunctions.currentRole";
CREATE ALIAS JSON_EXTRACT FOR "com.boilerplate.demo.h2.function.JsonFunctions.jsonExtract";
--- date
INSERT INTO permissions (id, name, is_active, is_default) VALUES (1, 'create:dat', 1, 1);
INSERT INTO permissions (id, name, is_active, is_default) VALUES (2, 'read:dat', 1, 1);

INSERT INTO roles (id, name, is_active, is_default) VALUES (1, 'ROLE_ADMIN', 1, 1);
INSERT INTO roles (id, name, is_active, is_default) VALUES (2, 'ROLE_USER', 1, 1);

INSERT INTO role_permissions(role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions(role_id, permission_id) VALUES (1, 2);

INSERT INTO role_permissions(role_id, permission_id) VALUES (2, 1);
INSERT INTO role_permissions(role_id, permission_id) VALUES (2, 2);

INSERT INTO users (id, user_name, first_name, last_name, password, retry_count, disabled, last_access_at, company_name, created_at, updated_at) VALUES ('admin', 'admin@example.com', 'Admin', 'User', '$2a$12$e4zwjOgwL.n7bA/0jvTEn.SbuLMNq5pT7qZJjI/nT6oSqAAImBVjq',     0, 0, null, 'Demo', 1599209626968, 1599209626968);
INSERT INTO users (id, user_name, first_name, last_name, password, retry_count, disabled, last_access_at, company_name, created_at, updated_at) VALUES ('user', 'user@example.com', 'User', 'One', '$2a$12$yzG.TGWBMS.azlI4mubryOf6aoRGCqinrJuxJHyQ2a5n9Dzotd98u', 0, 0, null, 'Demo', 1599209993950, 1599209993950);

INSERT INTO user_roles (user_id, role_id) VALUES ('admin',   1);
INSERT INTO user_roles (user_id, role_id) VALUES ('user', 2);

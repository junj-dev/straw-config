/*
 Navicat Premium Data Transfer

 Source Server         : 39.97.229.107
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 39.97.229.107:3306
 Source Schema         : straw

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 01/06/2020 10:54:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回答内容',
  `like_count` int(255) NOT NULL COMMENT '点赞数量',
  `user_id` int(11) NOT NULL COMMENT '回答问题的用户id',
  `user_nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回答者用户名',
  `quest_id` int(11) NOT NULL COMMENT '对应的问题id',
  `createtime` datetime(0) NOT NULL COMMENT '回答时间',
  `accept_status` tinyint(4) NOT NULL COMMENT '是否采纳',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answer
-- ----------------------------
INSERT INTO `answer` VALUES (1, '如果是MySql的话，先确定分库分表的规则，比如按主键取模，实施方案可以有两种：\r\n\r\n停服方案：首先按照分库分表库规则写好一个处理工具类，然后把现有的数据导入相应的库，这种办法比较笨重；\r\n双写机制（可不停服）：在涉及到增删改的地方，加上新的分库分表处理方案，这样在线上跑一段时间后，大部分的活跃数据也就会存在于新表，剩余少部分的数据，可以用上面的导入工具类在非活跃时段进行处理，这样就不用停服了。', 0, 1, '小陈', 13, '2020-03-13 10:41:40', 0);
INSERT INTO `answer` VALUES (2, 'oracle数据库可以使用分区，容量进一步大的话可考虑分表+分区。如果涉及跨分区甚至跨表统计，建议定时将统计数据写入新表中。', 0, 1, '小陈', 13, '2020-03-13 10:52:31', 0);
INSERT INTO `answer` VALUES (3, '看你们用的什么数据库，如果是oracle就好办了，直接使用分区就行了，按照日期分区。目前我们就是这么做的，我们数据量比你的要大多了，每个月都是上亿的数据，所以我们是按照日期分区的。如果是mysql那你就可能要分表，分库了。', 0, 1, '小陈', 13, '2020-03-13 10:52:52', 0);
INSERT INTO `answer` VALUES (4, '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">getBean这个方法是spring内部自己调用的。。。。在spring启动的时候，他会调用refresh方法会触发这个方法，然后跟据beandefinition里的配置的条件，会判断是否需要延迟加载，然后将类加载并实例化，你可以配置延迟加载，根据延迟加载的条件，它可以在你调用这个bean的时候实例化该对象</span><br></p>', 0, 1, '小陈', 14, '2020-03-13 13:01:33', 0);
INSERT INTO `answer` VALUES (5, '<p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h15rJveADyamAAEOO-Kw6X8804.jpg\" data-filename=\"img\" style=\"width: 809px;\"><br></p>', 0, 1, '小陈', 14, '2020-03-13 14:23:51', 0);
INSERT INTO `answer` VALUES (6, '<p>随便写写。。。。。。。。。。。。</p>', 0, 2, '花倩', 14, '2020-03-13 14:36:21', 0);
INSERT INTO `answer` VALUES (7, '<p>随便说说，，，，，，，，，，</p>', 0, 9, '刘苍松', 14, '2020-03-13 14:56:36', 0);
INSERT INTO `answer` VALUES (8, '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">你就把图片存在服务器上指定文件夹就行了。</span><br></p>', 0, 6, '成恒', 15, '2020-03-13 16:16:04', 0);
INSERT INTO `answer` VALUES (9, '<p>随便说点啥.......</p>', 0, 6, '成恒', 14, '2020-03-14 00:55:16', 0);
INSERT INTO `answer` VALUES (10, '<p>答案A</p>', 0, 6, '成恒', 18, '2020-03-16 20:02:17', 1);
INSERT INTO `answer` VALUES (11, '<p>随便写写</p>', 0, 1, 'admin', 14, '2020-03-16 21:44:42', 0);
INSERT INTO `answer` VALUES (12, '<p>随便写两句。。</p>', 0, 11, '李四', 18, '2020-03-16 21:45:22', 0);
INSERT INTO `answer` VALUES (13, '<p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154FGiAY2dJAALaarBrVyA915.png\" data-filename=\"img\" style=\"width: 981px;\"></p>', 0, 1, 'admin', 1, '2020-03-23 09:44:14', 0);
INSERT INTO `answer` VALUES (14, '<p>随便写写。。。</p>', 0, 9, '刘苍松', 28, '2020-03-23 10:01:04', 0);
INSERT INTO `answer` VALUES (15, '<p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154GGyAWBIOAAHTQf0oRLo363.png\" data-filename=\"img\" style=\"width: 903px;\"><br></p>', 0, 9, '刘苍松', 28, '2020-03-23 10:01:22', 0);
INSERT INTO `answer` VALUES (16, '<p>随便写写。。。。。</p>', 0, 9, '刘苍松', 7, '2020-03-23 10:12:41', 0);
INSERT INTO `answer` VALUES (17, '<p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154GyaAN4iSAAHTQf0oRLo183.png\" data-filename=\"img\" style=\"width: 903px;\"><br></p>', 0, 9, '刘苍松', 7, '2020-03-23 10:12:57', 0);
INSERT INTO `answer` VALUES (18, '<p>随便写写</p>', 0, 4, '王克晶', 2, '2020-03-25 11:32:13', 0);
INSERT INTO `answer` VALUES (19, '随便写写', 0, 4, '王克晶', 66, '2020-04-02 16:43:53', 0);
INSERT INTO `answer` VALUES (20, '<p>随便写写</p>', 0, 5, '刘国斌', 70, '2020-04-03 12:32:33', 0);
INSERT INTO `answer` VALUES (21, '<p>随便写两句</p>', 0, 5, '刘国斌', 63, '2020-04-03 13:00:18', 0);
INSERT INTO `answer` VALUES (22, '<p>随便写写</p><p><img src=\"http://localhost:8080/uploadFile/2020/04/03/ac18b72d-c041-4615-b46c-d602835cb8aa.png\" data-filename=\"img\" style=\"width: 1121px;\"><br></p>', 0, 9, '刘苍松', 63, '2020-04-03 14:21:01', 0);
INSERT INTO `answer` VALUES (23, '<p>随便写写</p>', 0, 5, '刘国斌', 62, '2020-04-03 14:34:05', 0);
INSERT INTO `answer` VALUES (24, '<p>随便写写</p>', 0, 5, '刘国斌', 61, '2020-04-03 14:34:16', 0);
INSERT INTO `answer` VALUES (25, '<p>随便写写</p>', 0, 4, '王克晶', 71, '2020-04-03 17:43:52', 0);
INSERT INTO `answer` VALUES (26, '<p>随便写写</p>', 0, 9, '刘苍松', 1, '2020-04-08 11:48:59', 0);
INSERT INTO `answer` VALUES (27, '<p>测试数据<img src=\"http://localhost:8080/uploadFile/2020/04/08/22a50d5e-4279-4b25-825b-067d4208f428.jpg\" data-filename=\"img\" style=\"width: 1088px;\"></p>', 0, 9, '刘苍松', 6, '2020-04-08 11:57:30', 0);
INSERT INTO `answer` VALUES (28, '<p>随便说说......</p>', 0, 4, '王克晶', 84, '2020-04-10 17:15:43', 0);
INSERT INTO `answer` VALUES (29, '<p>测试。。。。</p>', 0, 4, '王克晶', 10, '2020-04-10 17:26:51', 0);
INSERT INTO `answer` VALUES (30, '<p>测试。。。。</p>', 0, 4, '王克晶', 31, '2020-04-17 21:34:40', 1);
INSERT INTO `answer` VALUES (31, '<p>测试222</p>', 0, 4, '王克晶', 31, '2020-04-17 21:36:25', 1);
INSERT INTO `answer` VALUES (32, '<p>测试<img src=\"http://localhost:8083/uploadFile/2020/04/22/a2b057a8-2096-49a6-96cf-07a6602908be.png\" data-filename=\"img\" style=\"width: 708px;\"></p>', 0, 4, '王克晶', 106, '2020-04-22 16:59:19', 0);
INSERT INTO `answer` VALUES (33, '<p>测试</p><table class=\"table table-bordered\"><tbody><tr><td>ss</td><td><br></td><td><br></td><td><br></td></tr><tr><td>sss</td><td><br></td><td><br></td><td><br></td></tr><tr><td>ss</td><td><br></td><td><br></td><td><br></td></tr><tr><td>sss</td><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td><td><br></td></tr></tbody></table><p><br></p>', 0, 4, '王克晶', 106, '2020-04-22 17:14:04', 0);
INSERT INTO `answer` VALUES (34, '<p>测试一下</p>', 0, 9, '刘苍松', 26, '2020-04-26 15:12:25', 1);
INSERT INTO `answer` VALUES (35, '<p>测试回答2</p>', 0, 9, '刘苍松', 26, '2020-04-26 15:13:08', 1);
INSERT INTO `answer` VALUES (36, '<p>测试数据</p>', 0, 9, '刘苍松', 25, '2020-04-27 10:16:33', 1);
INSERT INTO `answer` VALUES (37, '<p>测试</p>', 0, 9, '刘苍松', 6, '2020-04-27 14:19:31', 0);
INSERT INTO `answer` VALUES (38, '<p>测试一下</p>', 0, 9, '刘苍松', 20, '2020-04-27 14:46:25', 1);
INSERT INTO `answer` VALUES (39, '<p>测试一下</p>', 0, 9, '刘苍松', 110, '2020-04-27 16:23:49', 1);
INSERT INTO `answer` VALUES (45, '<p>测试一下</p>', 0, 9, '刘苍松', 31, '2020-04-27 17:49:35', 0);
INSERT INTO `answer` VALUES (46, '<p>测试</p>', 0, 9, '刘苍松', 25, '2020-04-27 17:51:04', 1);
INSERT INTO `answer` VALUES (47, '<p>测试一下</p>', 0, 4, '王克晶', 31, '2020-04-28 00:25:34', 0);
INSERT INTO `answer` VALUES (48, '<p>测试</p>', 0, 9, '刘苍松', 109, '2020-04-28 10:54:25', 0);
INSERT INTO `answer` VALUES (49, '<p style=\"margin: 10px auto; padding: 0px; line-height: 1.5; color: rgb(0, 0, 0); font-size: 13px; text-indent: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(254, 254, 242); text-decoration-style: initial; text-decoration-color: initial;\">1.JSP与Java&nbsp;Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览</p><p style=\"margin: 10px auto; padding: 0px; line-height: 1.5; color: rgb(0, 0, 0); font-size: 13px; text-indent: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(254, 254, 242); text-decoration-style: initial; text-decoration-color: initial;\">2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求</p><p style=\"margin: 10px auto; padding: 0px; line-height: 1.5; color: rgb(0, 0, 0); font-size: 13px; text-indent: 0px; font-family: Verdana, Arial, Helvetica, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: left; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(254, 254, 242); text-decoration-style: initial; text-decoration-color: initial;\">3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关</p><p><b></b><i></i><u></u><sub></sub><sup></sup><strike></strike><br></p>', 0, 9, '刘苍松', 113, '2020-04-29 00:10:42', 1);
INSERT INTO `answer` VALUES (50, '<p><span style=\"color: rgb(52, 58, 64); background-color: rgba(0, 0, 0, 0.03);\">1.JSP与Java Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览 2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求 3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关</span><br></p>', 0, 9, '刘苍松', 113, '2020-05-02 15:35:00', 1);
INSERT INTO `answer` VALUES (51, '<p>测试</p>', 0, 9, '刘苍松', 118, '2020-05-06 11:21:18', 0);
INSERT INTO `answer` VALUES (52, '<p>测试</p>', 0, 9, '刘苍松', 114, '2020-05-06 16:12:32', 0);
INSERT INTO `answer` VALUES (60, '<ul class=\"list-paddingleft-2\" style=\'margin: 0px; padding: 0px 0px 0px 2.2em; max-width: 100%; box-sizing: border-box !important; overflow-wrap: break-word !important; color: rgb(51, 51, 51); font-family: -apple-system-font, BlinkMacSystemFont, \"Helvetica Neue\", \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei UI\", \"Microsoft YaHei\", Arial, sans-serif; font-size: 17px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: 0.544px; orphans: 2; text-align: justify; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;\'><li style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; overflow-wrap: break-word !important;\"><p style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; overflow-wrap: break-word !important; clear: both; min-height: 1em;\">JDK：Java Development Kit 的简称，java 开发工具包，提供了 java 的开发环境和运行环境。</p></li><li style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; overflow-wrap: break-word !important;\"><p style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; overflow-wrap: break-word !important; clear: both; min-height: 1em;\">JRE：Java Runtime Environment 的简称，java 运行环境，为 java 的运行提供了所需环境。</p></li></ul><p style=\'margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; overflow-wrap: break-word !important; clear: both; min-height: 1em; color: rgb(51, 51, 51); font-family: -apple-system-font, BlinkMacSystemFont, \"Helvetica Neue\", \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei UI\", \"Microsoft YaHei\", Arial, sans-serif; font-size: 17px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: 0.544px; orphans: 2; text-align: justify; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;\'><br style=\"margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; overflow-wrap: break-word !important;\"></p><p style=\'margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box !important; overflow-wrap: break-word !important; clear: both; min-height: 1em; color: rgb(51, 51, 51); font-family: -apple-system-font, BlinkMacSystemFont, \"Helvetica Neue\", \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei UI\", \"Microsoft YaHei\", Arial, sans-serif; font-size: 17px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: 0.544px; orphans: 2; text-align: justify; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;\'>具体来说 JDK 其实包含了 JRE，同时还包含了编译 java 源码的编译器 javac，还包含了很多 java 程序调试和分析的工具。简单来说：如果你需要运行 java 程序，只需安装 JRE 就可以了，如果你需要编写 java 程序，需要安装 JDK。</p><p><b></b><i></i><u></u><sub></sub><sup></sup><strike></strike><br></p>', 0, 4, '王克晶', 121, '2020-05-11 14:49:21', 1);
INSERT INTO `answer` VALUES (61, '<p>测试</p>', 0, 9, '刘苍松', 3, '2020-05-11 17:51:33', 0);
INSERT INTO `answer` VALUES (62, '<p>xxxxxxxxxxxxxxxxxxxxxxxxxaaaaa<img src=\"http://localhost:8080/uploadFile/2020/05/29/2ff531a0-eaba-47be-aa12-ca19a50a38f5.png\" data-filename=\"img\" style=\"width: 865px;\"></p>', 0, 2, '老师1', 9, '2020-05-29 09:45:10', 0);

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级名称',
  `invite_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邀请码',
  `enabled` tinyint(4) NOT NULL COMMENT '班级是否可用',
  `createtime` datetime(0) NOT NULL,
  `modifytime` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES (1, 'JSD1912', 'JSD1912-460913', 1, '2020-03-31 11:29:26', '2020-06-01 10:28:27');
INSERT INTO `classroom` VALUES (2, 'JSD2001', 'JSD2001-452852', 1, '2020-03-31 13:41:29', '2020-06-01 10:28:27');
INSERT INTO `classroom` VALUES (3, 'JSD2002', 'JSD2002-513766', 1, '2020-03-31 13:41:58', '2020-06-01 10:28:27');
INSERT INTO `classroom` VALUES (4, 'JSD2003', 'JSD2003-239960', 1, '2020-03-31 13:42:23', '2020-06-01 10:28:27');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `answer_id` int(11) NOT NULL COMMENT '回答id',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `createtime` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 9, 31, '测试一下看看', '2020-04-26 00:13:33');
INSERT INTO `comment` VALUES (2, 4, 31, '我也测试一下', '2020-04-26 14:49:53');
INSERT INTO `comment` VALUES (3, 11, 35, '不明白也', '2020-04-26 15:13:53');
INSERT INTO `comment` VALUES (4, 11, 35, '测试一下评论', '2020-04-26 15:14:08');
INSERT INTO `comment` VALUES (5, 11, 35, '测试3', '2020-04-26 15:25:57');
INSERT INTO `comment` VALUES (6, 9, 10, '对', '2020-04-27 14:21:22');
INSERT INTO `comment` VALUES (7, 9, 35, '测试一下', '2020-04-27 14:44:03');
INSERT INTO `comment` VALUES (8, 11, 38, '测试评论', '2020-04-27 14:46:56');
INSERT INTO `comment` VALUES (9, 11, 35, '什么', '2020-04-27 16:24:37');
INSERT INTO `comment` VALUES (10, 11, 35, '测试', '2020-04-27 16:53:56');
INSERT INTO `comment` VALUES (11, 11, 35, '测试一下', '2020-04-27 16:58:25');
INSERT INTO `comment` VALUES (12, 9, 39, '测试一下', '2020-04-28 00:18:01');
INSERT INTO `comment` VALUES (13, 4, 47, '测试222', '2020-04-28 00:25:46');
INSERT INTO `comment` VALUES (14, 9, 39, '测试111', '2020-04-28 09:57:28');
INSERT INTO `comment` VALUES (15, 4, 39, '不明白', '2020-04-28 09:59:16');
INSERT INTO `comment` VALUES (16, 4, 39, '测试一下', '2020-04-28 10:04:20');
INSERT INTO `comment` VALUES (17, 4, 39, '测试一下', '2020-04-28 12:20:31');
INSERT INTO `comment` VALUES (18, 4, 39, '测试测试2222', '2020-04-28 12:23:10');
INSERT INTO `comment` VALUES (19, 11, 35, '测试11', '2020-04-28 14:45:58');
INSERT INTO `comment` VALUES (20, 11, 35, '测试22', '2020-04-28 14:46:15');
INSERT INTO `comment` VALUES (21, 11, 34, 'balaba测试', '2020-04-28 16:18:08');
INSERT INTO `comment` VALUES (22, 11, 49, '明白了。谢谢老师！', '2020-04-29 00:12:18');
INSERT INTO `comment` VALUES (23, 11, 26, '测试', '2020-04-29 17:27:13');
INSERT INTO `comment` VALUES (24, 9, 49, '1.JSP与Java Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览\r\n\r\n2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求\r\n\r\n3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关', '2020-05-01 23:12:52');
INSERT INTO `comment` VALUES (25, 9, 49, '1.JSP与Java Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览\r\n\r\n2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求\r\n\r\n3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关', '2020-05-01 23:13:59');
INSERT INTO `comment` VALUES (26, 11, 35, '1.JSP与Java Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览\r\n\r\n2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求\r\n\r\n3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关', '2020-05-01 23:19:30');
INSERT INTO `comment` VALUES (27, 11, 46, '1.JSP与Java Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览\r\n\r\n2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求\r\n\r\n3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关', '2020-05-01 23:21:30');
INSERT INTO `comment` VALUES (28, 9, 26, '1.JSP与Java Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览\r\n\r\n2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求\r\n\r\n3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关', '2020-05-01 23:28:49');
INSERT INTO `comment` VALUES (29, 11, 50, '1.JSP与Java Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览 2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求 3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关', '2020-05-02 16:19:28');
INSERT INTO `comment` VALUES (30, 9, 50, '测试', '2020-05-06 10:27:23');
INSERT INTO `comment` VALUES (31, 9, 49, '1.JSP与Java Servlet一样，是在服务器端执行的，通常返回该客户端的就是一个HTML文本，因此客户端只要有浏览器就能浏览 2.在大多数Browser/Server结构的Web应用中，浏览器直接通过HTML或者JSP的形式与用户交互，响应用户的请求 3.JSP在服务器上执行，并将执行结果输出到客户端浏览器，我们可以说基本上与浏览器无关\r\n\r\n', '2020-05-06 10:51:29');
INSERT INTO `comment` VALUES (32, 9, 51, '测试1', '2020-05-06 11:21:25');
INSERT INTO `comment` VALUES (33, 9, 51, '测试2', '2020-05-06 11:21:34');
INSERT INTO `comment` VALUES (34, 9, 51, '测试3', '2020-05-06 11:22:27');
INSERT INTO `comment` VALUES (35, 9, 51, '测试4', '2020-05-06 11:23:33');
INSERT INTO `comment` VALUES (36, 11, 52, '测试', '2020-05-06 22:05:05');
INSERT INTO `comment` VALUES (44, 2, 62, '就这样aaaa\r\n', '2020-05-29 09:45:41');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '消息类型，0-》评论问题的回答，1-》回答某人的提问，2-》评论某人的提问，3-》向某人提问',
  `question_id` int(11) NOT NULL COMMENT '问题id',
  `createtime` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) NOT NULL COMMENT '收到消息的用户id',
  `reply_user_id` int(11) DEFAULT NULL COMMENT '回复者id',
  `read_status` tinyint(4) DEFAULT NULL COMMENT '消息是否已查看，0-》否，1-》是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 204 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (5, 1, 31, '2020-04-27 17:49:35', 23, 9, 0);
INSERT INTO `notice` VALUES (6, 1, 25, '2020-04-27 17:51:04', 11, 9, 1);
INSERT INTO `notice` VALUES (8, 1, 31, '2020-04-28 00:25:34', 23, 4, 0);
INSERT INTO `notice` VALUES (9, 2, 31, '2020-04-28 00:25:47', 23, 4, 0);
INSERT INTO `notice` VALUES (11, 0, 110, '2020-04-28 09:59:16', 9, 4, 1);
INSERT INTO `notice` VALUES (14, 1, 109, '2020-04-28 10:54:25', 4, 9, 0);
INSERT INTO `notice` VALUES (15, 0, 110, '2020-04-28 12:20:31', 9, 4, 1);
INSERT INTO `notice` VALUES (16, 0, 110, '2020-04-28 12:23:10', 9, 4, 1);
INSERT INTO `notice` VALUES (17, 0, 26, '2020-04-28 14:45:58', 9, 11, 1);
INSERT INTO `notice` VALUES (18, 0, 26, '2020-04-28 14:46:15', 9, 11, 1);
INSERT INTO `notice` VALUES (19, 0, 26, '2020-04-28 16:18:08', 9, 11, 0);
INSERT INTO `notice` VALUES (26, 3, 113, '2020-04-28 23:55:21', 9, 11, 0);
INSERT INTO `notice` VALUES (27, 3, 113, '2020-04-28 23:55:21', 8, 11, 0);
INSERT INTO `notice` VALUES (28, 3, 113, '2020-04-28 23:55:21', 5, 11, 0);
INSERT INTO `notice` VALUES (29, 3, 114, '2020-04-29 00:05:39', 9, 11, 0);
INSERT INTO `notice` VALUES (30, 3, 114, '2020-04-29 00:05:39', 6, 11, 0);
INSERT INTO `notice` VALUES (31, 3, 114, '2020-04-29 00:05:39', 8, 11, 0);
INSERT INTO `notice` VALUES (32, 1, 113, '2020-04-29 00:10:42', 11, 9, 1);
INSERT INTO `notice` VALUES (33, 0, 113, '2020-04-29 00:12:18', 9, 11, 1);
INSERT INTO `notice` VALUES (34, 0, 1, '2020-04-29 17:27:13', 9, 11, 0);
INSERT INTO `notice` VALUES (35, 2, 1, '2020-04-29 17:27:13', 13, 11, 0);
INSERT INTO `notice` VALUES (36, 2, 113, '2020-05-01 23:12:52', 11, 9, 0);
INSERT INTO `notice` VALUES (37, 2, 113, '2020-05-01 23:13:59', 11, 9, 0);
INSERT INTO `notice` VALUES (38, 0, 26, '2020-05-01 23:19:30', 9, 11, 0);
INSERT INTO `notice` VALUES (39, 0, 25, '2020-05-01 23:21:30', 9, 11, 1);
INSERT INTO `notice` VALUES (40, 2, 1, '2020-05-01 23:28:49', 13, 9, 0);
INSERT INTO `notice` VALUES (41, 1, 113, '2020-05-02 15:35:00', 11, 9, 0);
INSERT INTO `notice` VALUES (42, 0, 113, '2020-05-02 16:19:28', 9, 11, 0);
INSERT INTO `notice` VALUES (43, 3, 115, '2020-05-03 01:36:23', 2, 11, 1);
INSERT INTO `notice` VALUES (44, 3, 115, '2020-05-03 01:36:23', 4, 11, 0);
INSERT INTO `notice` VALUES (45, 3, 115, '2020-05-03 01:36:23', 9, 11, 0);
INSERT INTO `notice` VALUES (46, 3, 116, '2020-05-03 01:37:35', 2, 11, 1);
INSERT INTO `notice` VALUES (47, 3, 116, '2020-05-03 01:37:35', 4, 11, 0);
INSERT INTO `notice` VALUES (48, 3, 116, '2020-05-03 01:37:35', 9, 11, 0);
INSERT INTO `notice` VALUES (49, 3, 117, '2020-05-03 01:37:49', 2, 11, 1);
INSERT INTO `notice` VALUES (50, 3, 117, '2020-05-03 01:37:49', 4, 11, 0);
INSERT INTO `notice` VALUES (51, 3, 117, '2020-05-03 01:37:50', 9, 11, 0);
INSERT INTO `notice` VALUES (52, 3, 117, '2020-05-03 21:48:12', 2, 11, 1);
INSERT INTO `notice` VALUES (53, 3, 117, '2020-05-03 21:48:11', 2, 11, 1);
INSERT INTO `notice` VALUES (54, 3, 117, '2020-05-03 21:48:12', 4, 11, 0);
INSERT INTO `notice` VALUES (55, 3, 117, '2020-05-03 21:48:11', 4, 11, 0);
INSERT INTO `notice` VALUES (56, 3, 117, '2020-05-03 21:48:12', 9, 11, 0);
INSERT INTO `notice` VALUES (57, 3, 117, '2020-05-03 21:48:11', 9, 11, 0);
INSERT INTO `notice` VALUES (58, 3, 117, '2020-05-03 23:44:26', 2, 11, 1);
INSERT INTO `notice` VALUES (59, 3, 117, '2020-05-03 23:44:26', 2, 11, 1);
INSERT INTO `notice` VALUES (60, 3, 117, '2020-05-03 23:44:26', 4, 11, 0);
INSERT INTO `notice` VALUES (61, 3, 117, '2020-05-03 23:44:26', 4, 11, 0);
INSERT INTO `notice` VALUES (62, 3, 117, '2020-05-03 23:44:26', 9, 11, 0);
INSERT INTO `notice` VALUES (63, 3, 117, '2020-05-03 23:44:26', 9, 11, 0);
INSERT INTO `notice` VALUES (64, 3, 117, '2020-05-03 23:48:43', 2, 11, 1);
INSERT INTO `notice` VALUES (65, 3, 117, '2020-05-03 23:48:42', 2, 11, 1);
INSERT INTO `notice` VALUES (66, 3, 117, '2020-05-03 23:48:43', 4, 11, 0);
INSERT INTO `notice` VALUES (67, 3, 117, '2020-05-03 23:48:42', 4, 11, 0);
INSERT INTO `notice` VALUES (68, 3, 117, '2020-05-03 23:48:42', 9, 11, 0);
INSERT INTO `notice` VALUES (69, 3, 117, '2020-05-03 23:48:43', 9, 11, 0);
INSERT INTO `notice` VALUES (70, 3, 117, '2020-05-04 00:10:54', 2, 11, 1);
INSERT INTO `notice` VALUES (71, 3, 117, '2020-05-04 00:10:53', 2, 11, 1);
INSERT INTO `notice` VALUES (72, 3, 117, '2020-05-04 00:10:53', 4, 11, 0);
INSERT INTO `notice` VALUES (73, 3, 117, '2020-05-04 00:10:54', 4, 11, 0);
INSERT INTO `notice` VALUES (74, 3, 117, '2020-05-04 00:10:54', 9, 11, 0);
INSERT INTO `notice` VALUES (75, 3, 117, '2020-05-04 00:10:53', 9, 11, 0);
INSERT INTO `notice` VALUES (76, 3, 117, '2020-05-04 00:14:03', 2, 11, 1);
INSERT INTO `notice` VALUES (77, 3, 117, '2020-05-04 00:14:03', 2, 11, 1);
INSERT INTO `notice` VALUES (78, 3, 117, '2020-05-04 00:14:03', 4, 11, 0);
INSERT INTO `notice` VALUES (79, 3, 117, '2020-05-04 00:14:03', 4, 11, 0);
INSERT INTO `notice` VALUES (80, 3, 117, '2020-05-04 00:14:03', 9, 11, 0);
INSERT INTO `notice` VALUES (81, 3, 117, '2020-05-04 00:14:03', 9, 11, 0);
INSERT INTO `notice` VALUES (82, 3, 117, '2020-05-04 00:24:25', 2, 11, 1);
INSERT INTO `notice` VALUES (83, 3, 117, '2020-05-04 00:24:24', 2, 11, 1);
INSERT INTO `notice` VALUES (84, 3, 117, '2020-05-04 00:24:24', 4, 11, 0);
INSERT INTO `notice` VALUES (85, 3, 117, '2020-05-04 00:24:25', 4, 11, 0);
INSERT INTO `notice` VALUES (86, 3, 117, '2020-05-04 00:24:25', 9, 11, 0);
INSERT INTO `notice` VALUES (87, 3, 117, '2020-05-04 00:24:24', 9, 11, 0);
INSERT INTO `notice` VALUES (88, 3, 117, '2020-05-04 00:25:41', 2, 11, 1);
INSERT INTO `notice` VALUES (89, 3, 117, '2020-05-04 00:25:40', 2, 11, 1);
INSERT INTO `notice` VALUES (90, 3, 117, '2020-05-04 00:25:41', 4, 11, 0);
INSERT INTO `notice` VALUES (91, 3, 117, '2020-05-04 00:25:40', 4, 11, 0);
INSERT INTO `notice` VALUES (92, 3, 117, '2020-05-04 00:25:41', 9, 11, 0);
INSERT INTO `notice` VALUES (93, 3, 117, '2020-05-04 00:25:40', 9, 11, 0);
INSERT INTO `notice` VALUES (94, 3, 117, '2020-05-04 00:28:37', 2, 11, 1);
INSERT INTO `notice` VALUES (95, 3, 117, '2020-05-04 00:28:36', 2, 11, 1);
INSERT INTO `notice` VALUES (96, 3, 117, '2020-05-04 00:28:37', 4, 11, 0);
INSERT INTO `notice` VALUES (97, 3, 117, '2020-05-04 00:28:36', 4, 11, 0);
INSERT INTO `notice` VALUES (98, 3, 117, '2020-05-04 00:28:37', 9, 11, 0);
INSERT INTO `notice` VALUES (99, 3, 117, '2020-05-04 00:28:36', 9, 11, 0);
INSERT INTO `notice` VALUES (100, 3, 117, '2020-05-04 00:40:14', 2, 11, 1);
INSERT INTO `notice` VALUES (101, 3, 117, '2020-05-04 00:40:14', 2, 11, 1);
INSERT INTO `notice` VALUES (102, 3, 117, '2020-05-04 00:40:14', 4, 11, 0);
INSERT INTO `notice` VALUES (103, 3, 117, '2020-05-04 00:40:14', 4, 11, 0);
INSERT INTO `notice` VALUES (104, 3, 117, '2020-05-04 00:40:14', 9, 11, 0);
INSERT INTO `notice` VALUES (105, 3, 117, '2020-05-04 00:40:14', 9, 11, 1);
INSERT INTO `notice` VALUES (106, 3, 117, '2020-05-04 09:58:02', 2, 11, 1);
INSERT INTO `notice` VALUES (107, 3, 117, '2020-05-04 09:58:02', 2, 11, 1);
INSERT INTO `notice` VALUES (108, 3, 117, '2020-05-04 09:58:02', 4, 11, 0);
INSERT INTO `notice` VALUES (109, 3, 117, '2020-05-04 09:58:02', 4, 11, 0);
INSERT INTO `notice` VALUES (110, 3, 117, '2020-05-04 09:58:02', 9, 11, 1);
INSERT INTO `notice` VALUES (111, 3, 117, '2020-05-04 09:58:02', 9, 11, 0);
INSERT INTO `notice` VALUES (112, 3, 117, '2020-05-03 21:48:11', 2, 11, 1);
INSERT INTO `notice` VALUES (113, 3, 117, '2020-05-03 21:48:11', 4, 11, 0);
INSERT INTO `notice` VALUES (114, 3, 117, '2020-05-03 21:48:11', 9, 11, 0);
INSERT INTO `notice` VALUES (115, 3, 117, '2020-05-03 23:44:26', 2, 11, 1);
INSERT INTO `notice` VALUES (116, 3, 117, '2020-05-03 23:44:26', 4, 11, 0);
INSERT INTO `notice` VALUES (117, 3, 117, '2020-05-03 23:44:26', 9, 11, 0);
INSERT INTO `notice` VALUES (118, 3, 117, '2020-05-03 23:48:42', 2, 11, 1);
INSERT INTO `notice` VALUES (119, 3, 117, '2020-05-03 23:48:42', 4, 11, 0);
INSERT INTO `notice` VALUES (120, 3, 117, '2020-05-03 23:48:42', 9, 11, 0);
INSERT INTO `notice` VALUES (121, 3, 117, '2020-05-04 00:10:53', 2, 11, 1);
INSERT INTO `notice` VALUES (122, 3, 117, '2020-05-04 00:10:53', 4, 11, 0);
INSERT INTO `notice` VALUES (123, 3, 117, '2020-05-04 00:10:53', 9, 11, 0);
INSERT INTO `notice` VALUES (124, 3, 117, '2020-05-04 00:14:03', 2, 11, 1);
INSERT INTO `notice` VALUES (125, 3, 117, '2020-05-04 00:14:03', 4, 11, 0);
INSERT INTO `notice` VALUES (126, 3, 117, '2020-05-04 00:14:03', 9, 11, 0);
INSERT INTO `notice` VALUES (127, 3, 117, '2020-05-04 00:24:24', 2, 11, 1);
INSERT INTO `notice` VALUES (128, 3, 117, '2020-05-04 00:24:24', 4, 11, 0);
INSERT INTO `notice` VALUES (129, 3, 117, '2020-05-04 00:24:24', 9, 11, 0);
INSERT INTO `notice` VALUES (130, 3, 117, '2020-05-04 00:25:40', 2, 11, 1);
INSERT INTO `notice` VALUES (131, 3, 117, '2020-05-04 00:25:40', 4, 11, 0);
INSERT INTO `notice` VALUES (132, 3, 117, '2020-05-04 00:25:40', 9, 11, 0);
INSERT INTO `notice` VALUES (133, 3, 117, '2020-05-04 00:28:36', 2, 11, 1);
INSERT INTO `notice` VALUES (134, 3, 117, '2020-05-04 00:28:36', 4, 11, 0);
INSERT INTO `notice` VALUES (135, 3, 117, '2020-05-04 00:28:36', 9, 11, 0);
INSERT INTO `notice` VALUES (136, 3, 117, '2020-05-04 00:40:14', 2, 11, 1);
INSERT INTO `notice` VALUES (137, 3, 117, '2020-05-04 00:40:14', 4, 11, 0);
INSERT INTO `notice` VALUES (138, 3, 117, '2020-05-04 00:40:14', 9, 11, 0);
INSERT INTO `notice` VALUES (139, 3, 117, '2020-05-04 09:58:02', 2, 11, 1);
INSERT INTO `notice` VALUES (140, 3, 117, '2020-05-04 09:58:02', 4, 11, 0);
INSERT INTO `notice` VALUES (141, 3, 117, '2020-05-04 09:58:02', 9, 11, 1);
INSERT INTO `notice` VALUES (142, 3, 117, '2020-05-04 10:10:13', 2, 11, 1);
INSERT INTO `notice` VALUES (143, 3, 117, '2020-05-04 10:10:12', 2, 11, 1);
INSERT INTO `notice` VALUES (144, 3, 117, '2020-05-04 10:10:13', 4, 11, 0);
INSERT INTO `notice` VALUES (145, 3, 117, '2020-05-04 10:10:12', 4, 11, 0);
INSERT INTO `notice` VALUES (146, 3, 117, '2020-05-04 10:10:13', 9, 11, 1);
INSERT INTO `notice` VALUES (147, 3, 117, '2020-05-04 10:10:12', 9, 11, 1);
INSERT INTO `notice` VALUES (148, 3, 117, '2020-05-04 10:20:52', 2, 11, 1);
INSERT INTO `notice` VALUES (149, 3, 117, '2020-05-04 10:20:51', 2, 11, 1);
INSERT INTO `notice` VALUES (150, 3, 117, '2020-05-04 10:20:52', 4, 11, 0);
INSERT INTO `notice` VALUES (151, 3, 117, '2020-05-04 10:20:51', 4, 11, 0);
INSERT INTO `notice` VALUES (152, 3, 117, '2020-05-04 10:20:52', 9, 11, 1);
INSERT INTO `notice` VALUES (153, 3, 117, '2020-05-04 10:20:51', 9, 11, 0);
INSERT INTO `notice` VALUES (154, 3, 117, '2020-05-04 10:22:07', 2, 11, 1);
INSERT INTO `notice` VALUES (155, 3, 117, '2020-05-04 10:22:06', 2, 11, 1);
INSERT INTO `notice` VALUES (156, 3, 117, '2020-05-04 10:22:07', 4, 11, 1);
INSERT INTO `notice` VALUES (157, 3, 117, '2020-05-04 10:22:06', 4, 11, 0);
INSERT INTO `notice` VALUES (158, 3, 117, '2020-05-04 10:22:07', 9, 11, 0);
INSERT INTO `notice` VALUES (159, 3, 117, '2020-05-04 10:22:06', 9, 11, 0);
INSERT INTO `notice` VALUES (160, 3, 118, '2020-05-04 11:00:53', 9, 11, 0);
INSERT INTO `notice` VALUES (161, 3, 118, '2020-05-04 11:00:53', 5, 11, 0);
INSERT INTO `notice` VALUES (162, 2, 113, '2020-05-06 10:27:23', 11, 9, 0);
INSERT INTO `notice` VALUES (163, 2, 113, '2020-05-06 10:51:30', 11, 9, 1);
INSERT INTO `notice` VALUES (164, 1, 118, '2020-05-06 11:21:18', 11, 9, 1);
INSERT INTO `notice` VALUES (165, 2, 118, '2020-05-06 11:21:25', 11, 9, 0);
INSERT INTO `notice` VALUES (166, 2, 118, '2020-05-06 11:21:33', 11, 9, 0);
INSERT INTO `notice` VALUES (167, 2, 118, '2020-05-06 11:22:26', 11, 9, 1);
INSERT INTO `notice` VALUES (168, 2, 118, '2020-05-06 11:23:32', 11, 9, 1);
INSERT INTO `notice` VALUES (169, 1, 114, '2020-05-06 16:12:31', 11, 9, 0);
INSERT INTO `notice` VALUES (170, 3, 119, '2020-05-06 17:31:57', 9, 11, 0);
INSERT INTO `notice` VALUES (171, 0, 114, '2020-05-06 22:05:04', 9, 11, 0);
INSERT INTO `notice` VALUES (172, 1, 119, '2020-05-09 14:57:36', 11, 9, 1);
INSERT INTO `notice` VALUES (173, 0, 119, '2020-05-09 14:58:55', 9, 11, 0);
INSERT INTO `notice` VALUES (174, 2, 119, '2020-05-09 14:59:49', 11, 9, 0);
INSERT INTO `notice` VALUES (175, 2, 119, '2020-05-09 16:58:16', 11, 9, 0);
INSERT INTO `notice` VALUES (176, 1, 119, '2020-05-09 16:59:04', 11, 9, 0);
INSERT INTO `notice` VALUES (177, 0, 119, '2020-05-10 00:32:32', 9, 11, 0);
INSERT INTO `notice` VALUES (178, 0, 119, '2020-05-10 00:42:38', 9, 11, 0);
INSERT INTO `notice` VALUES (179, 1, 119, '2020-05-10 00:43:31', 11, 9, 0);
INSERT INTO `notice` VALUES (180, 1, 119, '2020-05-10 21:49:23', 11, 9, 0);
INSERT INTO `notice` VALUES (181, 2, 119, '2020-05-11 10:49:40', 11, 9, 0);
INSERT INTO `notice` VALUES (182, 1, 119, '2020-05-11 11:26:12', 11, 9, 0);
INSERT INTO `notice` VALUES (183, 1, 119, '2020-05-11 11:26:12', 11, 9, 0);
INSERT INTO `notice` VALUES (184, 1, 119, '2020-05-11 11:27:19', 11, 9, 0);
INSERT INTO `notice` VALUES (185, 1, 119, '2020-05-11 11:55:36', 11, 9, 0);
INSERT INTO `notice` VALUES (186, 3, 120, '2020-05-11 14:27:35', 4, 11, 1);
INSERT INTO `notice` VALUES (187, 3, 120, '2020-05-11 14:27:35', 9, 11, 0);
INSERT INTO `notice` VALUES (188, 3, 121, '2020-05-11 14:48:21', 4, 11, 1);
INSERT INTO `notice` VALUES (189, 3, 121, '2020-05-11 14:48:21', 9, 11, 0);
INSERT INTO `notice` VALUES (190, 1, 121, '2020-05-11 14:49:21', 11, 4, 1);
INSERT INTO `notice` VALUES (191, 0, 121, '2020-05-11 14:58:54', 4, 11, 1);
INSERT INTO `notice` VALUES (192, 3, 122, '2020-05-11 17:50:09', 9, 11, 0);
INSERT INTO `notice` VALUES (193, 1, 3, '2020-05-11 17:51:32', 13, 9, 1);
INSERT INTO `notice` VALUES (194, 3, 123, '2020-05-11 22:52:23', 4, 9, 0);
INSERT INTO `notice` VALUES (195, 3, 124, '2020-05-22 15:41:58', 2, 10, 1);
INSERT INTO `notice` VALUES (196, 3, 125, '2020-05-22 15:51:40', 3, 10, 0);
INSERT INTO `notice` VALUES (197, 3, 126, '2020-05-22 16:08:15', 3, 10, 0);
INSERT INTO `notice` VALUES (198, 3, 127, '2020-05-22 16:16:37', 28, 10, 0);
INSERT INTO `notice` VALUES (199, 3, 128, '2020-05-22 16:19:27', 33, 10, 0);
INSERT INTO `notice` VALUES (200, 1, 9, '2020-05-29 09:45:10', 13, 2, 0);
INSERT INTO `notice` VALUES (201, 2, 9, '2020-05-29 09:45:41', 13, 2, 0);
INSERT INTO `notice` VALUES (202, 0, 2, '2020-05-29 16:43:19', 4, 2, 0);
INSERT INTO `notice` VALUES (203, 2, 2, '2020-05-29 16:43:20', 13, 2, 0);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '/index.html', '访问主页');
INSERT INTO `permission` VALUES (2, '/question/create', '提问');
INSERT INTO `permission` VALUES (3, '/question/uploadMultipleFile', '上传图片文件');
INSERT INTO `permission` VALUES (4, '/question/detail', '查看问题');
INSERT INTO `permission` VALUES (5, '/question/answer', '回答问题');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题的标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提问内容',
  `user_nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提问者用户名',
  `user_id` int(11) NOT NULL COMMENT '提问者id',
  `createtime` datetime(0) NOT NULL COMMENT '创建时间',
  `modifytime` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `status` int(1) NOT NULL COMMENT '状态，0-》未回答，1-》待解决，2-》已解决',
  `page_views` int(11) NOT NULL COMMENT '浏览量',
  `public_status` int(1) NOT NULL COMMENT '该问题是否公开，所有学生都可见，0-》否，1-》是',
  `delete_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除状态，0-》否，1-》是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, '大量的if-eles要怎么优化?', '像这样有大量的if-else做判断，而且后续还会新增，每次新增都要再加一个if-else,有没有大佬来指导一下工作', '同学4', 13, '2020-03-09 14:39:25', NULL, 1, 69, 1, 1);
INSERT INTO `question` VALUES (2, 'jdk1.8对sycn做了什么优化？', 'jdk1.8对sycn做了什么优化？', '同学4', 13, '2020-03-09 15:12:28', NULL, 2, 57, 1, 0);
INSERT INTO `question` VALUES (3, 'Eureka的多活请问那节课有的啊?', '双活系统是怎么做的啊?哪里有啊?谢谢', '同学4', 13, '2020-03-09 23:39:39', NULL, 1, 39, 1, 0);
INSERT INTO `question` VALUES (4, '寻求mybatis二级缓存失效的解决方案', '我有一点疑问 ，关于 mybatis二级缓存的，因为二级缓存运用域在相同的namespace上，这时候这个namespace里面做了增删改，二级缓存就失效了，那我们可不可以采用多namespace在代码层面实现读写分离，也就是cqrs，这样是不是就可以避免二级缓存失效的问题', '同学4', 13, '2020-03-10 12:05:03', NULL, 0, 116, 0, 0);
INSERT INTO `question` VALUES (5, 'springIOC容器中的bean在什么时候被实例化和初始化？', '容器中的bean在被使用之前是不是都是没有初始化的？那有没有被实例化呢？还是说被使用之前一直都是以beandefinition的实例保存在容器中，直到getBean方法被调用才会实例化和初始化？', '同学4', 13, '2020-03-10 12:06:20', NULL, 0, 68, 0, 0);
INSERT INTO `question` VALUES (6, 'ES做全文搜索会过滤特殊符号怎么办?', 'boolQuery.should(QueryBuilders.matchPhrasePrefixQuery(s.fieldName(), word).boost(s.boostScore()));\r\n或\r\nboolQuery.should(QueryBuilders.matchPhraseQuery(s.fieldName(), word).boost(s.boostScore()));\r\n\r\n使用这个方法搜索 x2-2 结果搜出来的是x22的结果 - 被屏蔽掉\r\n\r\n使用了转义也没有用 QueryParser.escape(word.toLowerCase())', '同学4', 13, '2020-03-10 12:08:20', NULL, 1, 129, 0, 0);
INSERT INTO `question` VALUES (7, '阿里一道面试题', '题目：找出用户最爱歌曲风格\r\n假设有个用户唱歌的数据结构: Map<String, List> userSongs。 key是用户名，list是用户最近唱歌列表。\r\n有个歌曲风格的数据结构: Map<String, List> songGenres。 key是歌曲风格，list是歌曲列表。\r\n任务是返回一个用户最喜欢的歌曲风格map: Map<String, List>。 key是用户名，list是歌曲风格列表，list中的value是用户听的最多个歌曲对应的歌曲风格。', '同学4', 13, '2020-03-10 16:57:53', NULL, 2, 48, 1, 0);
INSERT INTO `question` VALUES (8, 'JVM判断垃圾对象问题', '判断一个对象是否是垃圾对象的方法有两种：引用计数法和可达性分析法。\r\n请问：这两种方法是怎么使用的？是针对不同的运行时区域有不同的垃圾对象判断还是两者混合使用？', '同学4', 13, '2020-03-10 16:58:33', NULL, 0, 41, 0, 0);
INSERT INTO `question` VALUES (9, 'docker', '为什么jack老师 课堂上，它也是使用的阿里云，在自己电脑上可以访问。我的为什么不行呢？', '同学4', 13, '2020-03-10 16:59:18', NULL, 1, 22, 0, 0);
INSERT INTO `question` VALUES (10, 'java反射', '有没有人清楚JDK1.7和1.8在反射调用实例方法上对参数检查处理上是否有差异？\r\n发现一段代码，对象有个com.edu.bean.A类型的属性type，反射调用setType(com.edu.A)，在1.7环境中运行正常，1.8就报类型不匹配了。\r\n【PS】：先不用管type是com.edu.bean.A类型，为啥setType传入的参数是com.edu.bean.A的问题，希望能从反射原理帮忙解释一下为什么1.7和1.8会出现不同的现象。', '同学4', 13, '2020-03-10 17:05:12', NULL, 1, 13, 0, 0);
INSERT INTO `question` VALUES (11, 'Feign报错feign.FeignException$BadRequest: ....', '测试', '同学4', 13, '2020-03-12 17:21:22', NULL, 2, 15, 1, 0);
INSERT INTO `question` VALUES (12, 'Zookeeper源码分析中的Leader选举问题 java', '<p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">类文件FastLeaderElection</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">// 缓存收到的票据<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">recvset.put(n.sid, new Vote(n.leader, n.zxid, n.electionEpoch, n.peerEpoch));</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">// Leader选举<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">if (termPredicate(recvset,new Vote(proposedLeader, proposedZxid,logicalclock.get(), proposedEpoch)))</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">// votes表示收到的外部选票的集合<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">// vote表示当前服务器的选票<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">protected boolean termPredicate(<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">HashMap&lt;Long, Vote&gt; votes,<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">Vote vote) {</p><pre style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 16px; font-family: SFMono-Regular, Consolas, &quot;Liberation Mono&quot;, Menlo, Courier, monospace; font-size: 13.6px; overflow-wrap: normal; line-height: 1.45; background-color: rgb(246, 248, 250); border-radius: 3px; color: rgb(36, 41, 46);\"><code style=\"-webkit-tap-highlight-color: transparent; margin: 0px; font-family: SFMono-Regular, Consolas, &quot;Liberation Mono&quot;, Menlo, Courier, monospace; font-size: 13.6px; background-image: initial; background-position: 0px 0px; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; border-radius: 3px; word-break: normal; white-space: pre; border: 0px; display: inline; overflow: visible; line-height: inherit; overflow-wrap: normal;\">HashSet&lt;Long&gt; set = new HashSet&lt;Long&gt;();\r\n\r\n/*\r\n * First make the views consistent. Sometimes peers will have\r\n * different zxids for a server depending on timing.\r\n *\r\n */\r\n// 遍历接收到的所有选票数据\r\nfor (Map.Entry&lt;Long,Vote&gt; entry : votes.entrySet()) {\r\n    //对选票进行归纳，就是把所有选票数据中和当前节点的票据相同的票据进行统计\r\n    if (vote.equals(entry.getValue())){ //对票据进行归纳\r\n        set.add(entry.getKey()); //如果存在2票，set里面是不是有2个？\r\n    }\r\n}\r\n\r\n//对选票进行判断\r\n// 判断当前节点的票数是否是大于一半，默认采用QuorumMaj来实现\r\nreturn self.getQuorumVerifier().containsQuorum(set); //验证\r\n</code></pre><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">}</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\">问题：假如有3台机器的zookeeper集群（server1,server2,server3），那votes的最多存在2票，如果要选出Leader，那么必须所有机器都要统一votes才行，这样不就和\"过半原则不符\"了吗？</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\"><br></p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\"><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h15qAfeAfFDJAAEOO-Kw6X8445.jpg\" data-filename=\"img\" style=\"width: 763px;\"><br></p>', '同学4', 13, '2020-03-12 17:33:46', NULL, 2, 14, 1, 0);
INSERT INTO `question` VALUES (13, '数据库已存在的大数据量的表如何进行分表', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">由于历史原因，系统中有一张表数据量逐年变大，变到了几千万甚至上亿，这些数据不能像日志一样定时归档和删除，求问怎么做分表？</span><br></p>', '同学4', 13, '2020-03-13 10:51:36', NULL, 0, 2, 0, 0);
INSERT INTO `question` VALUES (14, 'springIOC容器中的bean在什么时候被实例化和初始化', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">容器中的bean在被使用之前是不是都是没有初始化的？那有没有被实例化呢？还是说被使用之前一直都是以beandefinition的实例保存在容器中，直到getBean方法被调用才会实例化和初始化？</span><br></p>', '同学4', 13, '2020-03-13 12:21:16', NULL, 1, 5, 0, 0);
INSERT INTO `question` VALUES (15, 'springboot上传图片问题', '<p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">使用springboot 上传图片指定到 resources/public目录下，可以通过浏览器直接访问。<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">如上传的文件为<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">resources/public/a.jpg</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">访问路径 http://host:port/a.jpg</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">然而， springboot打包后是一个jar文件，那么public目录无法写文件，造成上传图片功能不可用，请问使用springboot上传到指定目录后通过应用服务如何访问？</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\">前提，没有图片服务器，是通过 应用服务器访问的图片</p>', '老师3', 3, '2020-03-13 16:03:12', NULL, 1, 4, 0, 0);
INSERT INTO `question` VALUES (16, 'JSON方式实现深克隆', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">不明白为什么Java转成JSON，JSON再转成Java就成深克隆了。。</span></p><p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h15uTu2AKJ5eAALaarBrVyA489.png\" data-filename=\"img\" style=\"width: 665px;\"><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\"><br></span><br></p>', '同学2', 11, '2020-03-15 23:51:10', NULL, 0, 1, 0, 0);
INSERT INTO `question` VALUES (17, 'curl端口访问不通的问题', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">我这边centos7虚拟机通过curl http://127.0.0.1:5601访问本机端口是正常的,但是通过具体的curl http://具体ip:5601就不行,想请问一下是什么原因</span><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h15vIJmAJ_DrAAHTQf0oRLo063.png\" data-filename=\"img\" style=\"width: 665px;\"><br></p>', '同学2', 11, '2020-03-16 14:45:46', NULL, 0, 13, 0, 0);
INSERT INTO `question` VALUES (18, '【大厂面试题2020/3/15】', '<p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">关于JSP生命周期的叙述，下列哪些为真?</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\">A、JSP会先解释成Servlet源文件，然后编译成Servlet类文件<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">B、每当用户端运行JSP时，jsp init()方法都会运行一次<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">C、每当用户端运行JSP时，jsp service()方法都会运行一次<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">D、每当用户端运行JSP时，jsp destroy()方法都会运行一次</p>', '同学2', 11, '2020-03-16 15:20:12', NULL, 2, 34, 0, 0);
INSERT INTO `question` VALUES (19, 'dubbo负载均衡问题', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">服务端已限制每个节点的dubbo.protocol.threads限制数为600，尝试过使用各种负载均衡策略，及调整每个节点的负载权重，但并发较高时仍是不能分配均衡，总有几个节点会满负载，不清楚是什么原因造成的，该如何解决？</span><br></p>', '同学2', 11, '2020-03-19 09:52:40', NULL, 0, 2, 0, 0);
INSERT INTO `question` VALUES (20, '数据库已存在的大数据量的表如何进行分表', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">由于历史原因，系统中有一张表数据量逐年变大，变到了几千万甚至上亿，这些数据不能像日志一样定时归档和删除，求问怎么做分表？</span><br></p>', '同学2', 11, '2020-03-19 12:05:20', NULL, 2, 17, 0, 0);
INSERT INTO `question` VALUES (25, '求一个优化的薪酬计算方案', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">薪酬计算方案：目前很简单，就是根据不同薪酬计算方式归类，然后分步计算后加入到待入库集合中统一插入，但是在计算过程中需要请求多次数据库，因为不同的计算方式都在数据库中保存，哪位大神帮忙提供下方案！！！</span><br></p>', '同学2', 11, '2020-03-19 14:57:38', NULL, 2, 105, 0, 0);
INSERT INTO `question` VALUES (26, 'springboot内置tomcat配置疑问', '<p><span style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px; font-weight: 600; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">spring-boot-autoconfigure 版本为1.5.9 和 2.0.1时，如图所示，很多tomact参数为0，如最大线程maxThreads，在 2.0.7版本中最大线程为200，<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">那么问题来了，我们平时看资料说 springboot内置tomcat默认线程为200，那么在1.5.9 和 2.0.1默认最大线程为0的情况下，如果不显示声明线程池大小的情况下，那么该项目的最大线程是否就为0？大佬们解答一下我的疑问</span><br></p>', '同学2', 11, '2020-03-19 16:14:17', NULL, 1, 230, 0, 0);
INSERT INTO `question` VALUES (27, '面试题', '<p>xxxxxxxxxxxxxx<img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154FA-AfZpxAAHTQf0oRLo407.png\" data-filename=\"img\" style=\"width: 809px;\"></p>', 'admin', 1, '2020-03-23 09:42:45', NULL, 0, 3, 0, 0);
INSERT INTO `question` VALUES (28, '面试题？？？？', '<p>xxxxxxxx<img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154F92AMyQhAALaarBrVyA279.png\" data-filename=\"img\" style=\"width: 809px;\"></p>', '同学4', 13, '2020-03-23 09:58:56', NULL, 1, 41, 0, 0);
INSERT INTO `question` VALUES (29, '什么是springcloud???', '<p>xxxxxxxx<img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154GoWAbVLlAALaarBrVyA395.png\" data-filename=\"img\" style=\"width: 809px;\"></p>', '同学4', 13, '2020-03-23 10:10:18', NULL, 0, 37, 0, 0);
INSERT INTO `question` VALUES (105, '测试1s', '<p><img src=\"http://localhost:8083/uploadFile/2020/04/20/2f37fe08-6dbe-4c0d-a874-98f45969ac38.jpg\" data-filename=\"img\" style=\"width: 708px;\"><br></p>', '老师3', 4, '2020-04-20 12:17:29', '2020-05-11 15:01:06', 0, 6, 0, 1);
INSERT INTO `question` VALUES (106, '测试1s', '<p><img src=\"http://localhost:8083/uploadFile/2020/04/20/2f37fe08-6dbe-4c0d-a874-98f45969ac38.jpg\" data-filename=\"img\" style=\"width: 708px;\"><br></p>', '老师3', 4, '2020-04-20 12:17:42', '2020-05-11 15:00:52', 1, 36, 0, 1);
INSERT INTO `question` VALUES (109, '测试22', '<p>测试<img src=\"http://localhost:8083/uploadFile/2020/04/22/4b1a1fd9-4c6f-4653-a328-6be2f32f664c.png\" data-filename=\"img\" style=\"width: 708px;\"></p>', '老师3', 4, '2020-04-22 17:18:55', NULL, 1, 7, 0, 1);
INSERT INTO `question` VALUES (110, '测试22', '<p>测试<img src=\"http://localhost:8083/uploadFile/2020/04/22/4b1a1fd9-4c6f-4653-a328-6be2f32f664c.png\" data-filename=\"img\" style=\"width: 100%;\"></p>', '老师3', 4, '2020-04-22 17:19:20', '2020-05-11 15:00:43', 2, 93, 0, 1);
INSERT INTO `question` VALUES (113, '浏览器jsp，html之间的关系', '<h2 style=\"margin: 10px 0px; padding: 0px; font-size: 21px; line-height: 1.5; font-family: Verdana, Arial, Helvetica, sans-serif; background-color: rgb(254, 254, 242);\">浏览器jsp，html之间的关系</h2>', '同学2', 11, '2020-04-28 23:55:21', NULL, 2, 133, 0, 1);
INSERT INTO `question` VALUES (114, '自定义标签要继承哪个类', '<h2 style=\"margin: 10px 0px; padding: 0px; font-size: 21px; line-height: 1.5; font-family: Verdana, Arial, Helvetica, sans-serif; background-color: rgb(254, 254, 242);\">自定义标签要继承哪个类</h2>', '同学2', 11, '2020-04-29 00:05:38', NULL, 2, 287, 1, 0);
INSERT INTO `question` VALUES (115, 'summernote富文本编辑器的基本使用', '<p>$(\'#summernote\').summernote({</p><p>&nbsp;</p><p>&nbsp;height: 300,&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;// set editor height</p><p>&nbsp;</p><p>&nbsp; minHeight:null,&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;// set minimum heightof editor</p><p>&nbsp;</p><p>&nbsp; maxHeight:null,&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;// set maximum heightof editor</p><p>&nbsp;</p><p>&nbsp; focus: true&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; // set focus to editable areaafter initializing summernote</p><p>&nbsp;</p><p>});</p>', '同学2', 11, '2020-05-03 01:36:23', NULL, 0, 3, 0, 1);
INSERT INTO `question` VALUES (116, 'summernote富文本编辑器的基本使用', '$(\'#summernote\').summernote({\n height: 300,                 // set editor height\n  minHeight:null,             // set minimum heightof editor\n  maxHeight:null,             // set maximum heightof editor\n  focus: true                  // set focus to editable areaafter initializing summernote\n});', '同学2', 11, '2020-05-03 01:37:35', '2020-05-06 16:00:52', 0, 5, 0, 1);
INSERT INTO `question` VALUES (117, 'summernote', '$(\'#summernote\').summernote({\n height: 300,                 // set editor height\n  minHeight:null,             // set minimum heightof editor\n  maxHeight:null,             // set maximum heightof editor\n  focus: true                  // set focus to editable areaafter initializing summernote\n});qqqqqqqqqqqqqqaaabbccssfffbb', '同学2', 11, '2020-05-03 01:37:49', '2020-05-06 16:00:46', 0, 52, 0, 1);
INSERT INTO `question` VALUES (118, '报错信息', '<pre style=\"\"><font color=\"#458383\" face=\"Consolas\">java.net.SocketTimeoutException: 30,000 milliseconds timeout on connection http-outgoing-8 [ACTIVE]</font><font color=\"#000000\" face=\"Consolas\"><span style=\"font-size: 10.5pt;\"><br></span></font></pre>', '同学2', 11, '2020-05-04 11:00:53', '2020-05-06 14:41:15', 1, 31, 0, 1);
INSERT INTO `question` VALUES (119, '什么是JSP', '<p><img src=\"http://localhost:8080/uploadFile/2020/05/06/dca06037-3c87-422a-b2f1-3120f2d22c50.png\" data-filename=\"img\" style=\"width: 708px;\"><br></p>', '同学2', 11, '2020-05-06 17:31:58', NULL, 0, 358, 0, 0);
INSERT INTO `question` VALUES (120, 'JDK 和 JRE 有什么区别？', '<h6><span style=\"color: rgb(33, 37, 41); font-size: x-large; text-align: center; font-family: &quot;Comic Sans MS&quot;;\">JDK 和 JRE 有什么区别？</span></h6>', '同学2', 11, '2020-05-11 14:27:35', '2020-05-11 14:47:12', 0, 21, 0, 1);
INSERT INTO `question` VALUES (121, 'JDK 和 JRE 有什么区别？', '<p>JDK和JRE有什么区别？</p>', '同学2', 11, '2020-05-11 14:48:22', '2020-05-11 15:02:59', 2, 17, 0, 1);
INSERT INTO `question` VALUES (122, '测试111', '<p>测试</p>', '同学2', 11, '2020-05-11 17:50:09', NULL, 0, 4, 0, 0);
INSERT INTO `question` VALUES (123, '测试111', '<p>测试</p>', '老师8', 9, '2020-05-11 22:52:23', NULL, 0, 1, 0, 0);
INSERT INTO `question` VALUES (124, '客户端和服务器端之间的通讯是基于什么', '<p><span style=\"color: rgb(33, 37, 41); background-color: rgb(248, 249, 250);\">老师,请问客户端和服务器端的通讯是怎么通讯的啊,Socket是什么?</span><br></p>', '同学1', 10, '2020-05-22 15:41:58', '2020-05-22 15:51:00', 0, 3, 0, 1);
INSERT INTO `question` VALUES (125, '客户端和服务器端之间的通讯是基于什么', '<p><span style=\"color: rgb(33, 37, 41); background-color: rgb(248, 249, 250);\">老师,请问客户端和服务器端的通讯是怎么通讯的啊,Socket是什么?</span><br></p>', '同学1', 10, '2020-05-22 15:51:40', '2020-05-22 16:07:45', 0, 2, 0, 1);
INSERT INTO `question` VALUES (126, '客户端和服务器端之间的通讯是基于什么', '<p><span style=\"color: rgb(33, 37, 41); background-color: rgb(248, 249, 250);\">老师,请问客户端和服务器端的通讯是怎么通讯的啊,Socket是什么?</span><br></p>', '同学1', 10, '2020-05-22 16:08:15', '2020-05-22 16:16:22', 0, 1, 0, 1);
INSERT INTO `question` VALUES (127, '测试111', '<p>测试</p>', '同学1', 10, '2020-05-22 16:16:38', '2020-05-22 16:19:13', 0, 1, 0, 1);
INSERT INTO `question` VALUES (128, '测试1111', '<p>测试111</p>', '同学1', 10, '2020-05-22 16:19:28', NULL, 0, 2, 0, 0);

-- ----------------------------
-- Table structure for question_tag
-- ----------------------------
DROP TABLE IF EXISTS `question_tag`;
CREATE TABLE `question_tag`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `question_id` int(20) NOT NULL,
  `tag_id` int(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 254 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_tag
-- ----------------------------
INSERT INTO `question_tag` VALUES (1, 1, 1);
INSERT INTO `question_tag` VALUES (2, 2, 1);
INSERT INTO `question_tag` VALUES (3, 3, 14);
INSERT INTO `question_tag` VALUES (4, 1, 14);
INSERT INTO `question_tag` VALUES (5, 4, 11);
INSERT INTO `question_tag` VALUES (6, 5, 9);
INSERT INTO `question_tag` VALUES (7, 6, 16);
INSERT INTO `question_tag` VALUES (8, 7, 15);
INSERT INTO `question_tag` VALUES (9, 8, 1);
INSERT INTO `question_tag` VALUES (10, 9, 17);
INSERT INTO `question_tag` VALUES (11, 10, 1);
INSERT INTO `question_tag` VALUES (12, 11, 9);
INSERT INTO `question_tag` VALUES (13, 11, 14);
INSERT INTO `question_tag` VALUES (14, 12, 14);
INSERT INTO `question_tag` VALUES (15, 12, 15);
INSERT INTO `question_tag` VALUES (16, 13, 1);
INSERT INTO `question_tag` VALUES (17, 13, 7);
INSERT INTO `question_tag` VALUES (18, 14, 9);
INSERT INTO `question_tag` VALUES (19, 14, 15);
INSERT INTO `question_tag` VALUES (20, 15, 13);
INSERT INTO `question_tag` VALUES (21, 16, 1);
INSERT INTO `question_tag` VALUES (22, 16, 15);
INSERT INTO `question_tag` VALUES (23, 17, 18);
INSERT INTO `question_tag` VALUES (24, 17, 19);
INSERT INTO `question_tag` VALUES (25, 18, 15);
INSERT INTO `question_tag` VALUES (26, 19, 20);
INSERT INTO `question_tag` VALUES (27, 20, 1);
INSERT INTO `question_tag` VALUES (28, 20, 7);
INSERT INTO `question_tag` VALUES (34, 25, 1);
INSERT INTO `question_tag` VALUES (35, 26, 6);
INSERT INTO `question_tag` VALUES (36, 27, 1);
INSERT INTO `question_tag` VALUES (37, 27, 15);
INSERT INTO `question_tag` VALUES (38, 28, 1);
INSERT INTO `question_tag` VALUES (39, 28, 15);
INSERT INTO `question_tag` VALUES (40, 29, 14);
INSERT INTO `question_tag` VALUES (41, 29, 15);
INSERT INTO `question_tag` VALUES (43, 31, 15);
INSERT INTO `question_tag` VALUES (186, 105, 7);
INSERT INTO `question_tag` VALUES (187, 105, 14);
INSERT INTO `question_tag` VALUES (188, 106, 7);
INSERT INTO `question_tag` VALUES (189, 106, 14);
INSERT INTO `question_tag` VALUES (194, 109, 2);
INSERT INTO `question_tag` VALUES (195, 109, 7);
INSERT INTO `question_tag` VALUES (196, 110, 2);
INSERT INTO `question_tag` VALUES (197, 110, 7);
INSERT INTO `question_tag` VALUES (202, 113, 8);
INSERT INTO `question_tag` VALUES (203, 113, 15);
INSERT INTO `question_tag` VALUES (204, 114, 1);
INSERT INTO `question_tag` VALUES (205, 114, 8);
INSERT INTO `question_tag` VALUES (206, 115, 15);
INSERT INTO `question_tag` VALUES (207, 115, 6);
INSERT INTO `question_tag` VALUES (208, 116, 15);
INSERT INTO `question_tag` VALUES (209, 116, 6);
INSERT INTO `question_tag` VALUES (236, 117, 15);
INSERT INTO `question_tag` VALUES (237, 117, 6);
INSERT INTO `question_tag` VALUES (238, 118, 13);
INSERT INTO `question_tag` VALUES (239, 118, 14);
INSERT INTO `question_tag` VALUES (240, 119, 8);
INSERT INTO `question_tag` VALUES (241, 120, 1);
INSERT INTO `question_tag` VALUES (242, 120, 15);
INSERT INTO `question_tag` VALUES (243, 121, 1);
INSERT INTO `question_tag` VALUES (244, 121, 15);
INSERT INTO `question_tag` VALUES (245, 122, 1);
INSERT INTO `question_tag` VALUES (246, 122, 15);
INSERT INTO `question_tag` VALUES (247, 123, 4);
INSERT INTO `question_tag` VALUES (248, 123, 15);
INSERT INTO `question_tag` VALUES (249, 124, 3);
INSERT INTO `question_tag` VALUES (250, 125, 3);
INSERT INTO `question_tag` VALUES (251, 126, 3);
INSERT INTO `question_tag` VALUES (252, 127, 8);
INSERT INTO `question_tag` VALUES (253, 128, 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN');
INSERT INTO `role` VALUES (2, 'ROLE_STUDENT');
INSERT INTO `role` VALUES (3, 'ROLE_TEACHER');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 1, 5);
INSERT INTO `role_permission` VALUES (6, 2, 1);
INSERT INTO `role_permission` VALUES (7, 2, 2);
INSERT INTO `role_permission` VALUES (8, 2, 3);
INSERT INTO `role_permission` VALUES (9, 2, 4);
INSERT INTO `role_permission` VALUES (11, 3, 1);
INSERT INTO `role_permission` VALUES (12, 3, 2);
INSERT INTO `role_permission` VALUES (13, 3, 3);
INSERT INTO `role_permission` VALUES (14, 3, 4);
INSERT INTO `role_permission` VALUES (15, 3, 5);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `createby` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `createtime` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 'Java基础', 'admin', '2020-03-09 14:39:48');
INSERT INTO `tag` VALUES (2, 'Java OOP', 'admin', '2020-03-09 23:30:09');
INSERT INTO `tag` VALUES (3, 'Java SE', 'admin', '2020-03-09 23:32:13');
INSERT INTO `tag` VALUES (4, 'WebServer', 'admin', '2020-03-09 23:32:50');
INSERT INTO `tag` VALUES (5, '二进制', 'admin', '2020-03-09 23:33:18');
INSERT INTO `tag` VALUES (6, 'Web', 'admin', '2020-03-09 23:33:58');
INSERT INTO `tag` VALUES (7, 'Mysql', 'admin', '2020-03-09 23:34:20');
INSERT INTO `tag` VALUES (8, 'Servlet', 'admin', '2020-03-09 23:34:40');
INSERT INTO `tag` VALUES (9, 'Spring', 'admin', '2020-03-09 23:34:58');
INSERT INTO `tag` VALUES (10, 'SpringMVC', 'admin', '2020-03-09 23:35:17');
INSERT INTO `tag` VALUES (11, 'Mybatis', 'admin', '2020-03-09 23:35:38');
INSERT INTO `tag` VALUES (12, 'Ajax', 'admin', '2020-03-09 23:36:02');
INSERT INTO `tag` VALUES (13, 'SpringBoot', 'admin', '2020-03-09 23:36:22');
INSERT INTO `tag` VALUES (14, 'SpringCloud', 'admin', '2020-03-09 23:36:43');
INSERT INTO `tag` VALUES (15, '面试题', 'admin', '2020-03-09 23:37:28');
INSERT INTO `tag` VALUES (16, '搜索引擎', 'admin', '2020-03-09 23:40:47');
INSERT INTO `tag` VALUES (17, 'docker', 'admin', '2020-03-10 17:19:05');
INSERT INTO `tag` VALUES (18, 'linux', 'admin', '2020-03-16 14:44:04');
INSERT INTO `tag` VALUES (19, 'centos', 'admin', '2020-03-16 14:44:22');
INSERT INTO `tag` VALUES (20, 'dubbo', 'admin', '2020-03-19 09:52:09');

-- ----------------------------
-- Table structure for teacher_question
-- ----------------------------
DROP TABLE IF EXISTS `teacher_question`;
CREATE TABLE `teacher_question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `createtime` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 265 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_question
-- ----------------------------
INSERT INTO `teacher_question` VALUES (1, 3, 1, '2020-04-01 12:48:50');
INSERT INTO `teacher_question` VALUES (2, 3, 2, '2020-04-01 12:48:58');
INSERT INTO `teacher_question` VALUES (3, 3, 3, '2020-04-01 12:49:05');
INSERT INTO `teacher_question` VALUES (4, 3, 4, '2020-04-01 12:49:10');
INSERT INTO `teacher_question` VALUES (5, 3, 5, '2020-04-01 12:49:21');
INSERT INTO `teacher_question` VALUES (6, 3, 6, '2020-04-01 12:49:27');
INSERT INTO `teacher_question` VALUES (7, 3, 7, '2020-04-01 12:49:35');
INSERT INTO `teacher_question` VALUES (8, 3, 8, '2020-04-01 12:49:42');
INSERT INTO `teacher_question` VALUES (9, 3, 9, '2020-04-01 12:49:49');
INSERT INTO `teacher_question` VALUES (10, 3, 10, '2020-04-01 12:49:56');
INSERT INTO `teacher_question` VALUES (11, 3, 11, '2020-04-01 12:50:03');
INSERT INTO `teacher_question` VALUES (12, 3, 12, '2020-04-01 12:50:13');
INSERT INTO `teacher_question` VALUES (91, 4, 70, '2020-04-03 11:18:20');
INSERT INTO `teacher_question` VALUES (92, 8, 70, '2020-04-03 12:58:44');
INSERT INTO `teacher_question` VALUES (93, 8, 63, '2020-04-03 14:19:56');
INSERT INTO `teacher_question` VALUES (94, 8, 47, '2020-04-03 15:45:59');
INSERT INTO `teacher_question` VALUES (95, 8, 46, '2020-04-03 15:45:59');
INSERT INTO `teacher_question` VALUES (96, 8, 50, '2020-04-03 15:47:29');
INSERT INTO `teacher_question` VALUES (97, 8, 43, '2020-04-03 15:48:16');
INSERT INTO `teacher_question` VALUES (98, 8, 42, '2020-04-03 15:48:16');
INSERT INTO `teacher_question` VALUES (99, 8, 53, '2020-04-03 15:49:25');
INSERT INTO `teacher_question` VALUES (100, 8, 52, '2020-04-03 15:49:25');
INSERT INTO `teacher_question` VALUES (101, 3, 71, '2020-04-03 17:43:11');
INSERT INTO `teacher_question` VALUES (102, 4, 71, '2020-04-03 17:43:11');
INSERT INTO `teacher_question` VALUES (103, 8, 12, '2020-04-03 17:44:47');
INSERT INTO `teacher_question` VALUES (107, 3, 74, '2020-04-08 10:36:45');
INSERT INTO `teacher_question` VALUES (108, 4, 74, '2020-04-08 10:36:45');
INSERT INTO `teacher_question` VALUES (109, 2, 75, '2020-04-08 15:48:28');
INSERT INTO `teacher_question` VALUES (110, 4, 75, '2020-04-08 15:48:28');
INSERT INTO `teacher_question` VALUES (111, 1, 10, '2020-04-09 17:18:02');
INSERT INTO `teacher_question` VALUES (112, 8, 10, '2020-04-09 17:22:50');
INSERT INTO `teacher_question` VALUES (113, 7, 10, '2020-04-09 17:30:18');
INSERT INTO `teacher_question` VALUES (114, 2, 9, '2020-04-09 17:32:19');
INSERT INTO `teacher_question` VALUES (115, 2, 4, '2020-04-09 17:33:11');
INSERT INTO `teacher_question` VALUES (116, 2, 10, '2020-04-09 19:09:11');
INSERT INTO `teacher_question` VALUES (117, 4, 7, '2020-04-09 19:22:39');
INSERT INTO `teacher_question` VALUES (178, 4, 105, '2020-04-20 12:17:29');
INSERT INTO `teacher_question` VALUES (179, 8, 105, '2020-04-20 12:17:29');
INSERT INTO `teacher_question` VALUES (180, 4, 106, '2020-04-20 12:17:42');
INSERT INTO `teacher_question` VALUES (181, 8, 106, '2020-04-20 12:17:42');
INSERT INTO `teacher_question` VALUES (186, 4, 109, '2020-04-22 17:18:55');
INSERT INTO `teacher_question` VALUES (187, 8, 109, '2020-04-22 17:18:55');
INSERT INTO `teacher_question` VALUES (188, 4, 110, '2020-04-22 17:19:20');
INSERT INTO `teacher_question` VALUES (189, 8, 110, '2020-04-22 17:19:20');
INSERT INTO `teacher_question` VALUES (190, 9, 109, '2020-04-28 10:53:42');
INSERT INTO `teacher_question` VALUES (197, 9, 113, '2020-04-28 23:55:21');
INSERT INTO `teacher_question` VALUES (198, 8, 113, '2020-04-28 23:55:21');
INSERT INTO `teacher_question` VALUES (199, 5, 113, '2020-04-28 23:55:21');
INSERT INTO `teacher_question` VALUES (200, 9, 114, '2020-04-29 00:05:39');
INSERT INTO `teacher_question` VALUES (201, 6, 114, '2020-04-29 00:05:39');
INSERT INTO `teacher_question` VALUES (202, 8, 114, '2020-04-29 00:05:39');
INSERT INTO `teacher_question` VALUES (203, 2, 115, '2020-05-03 01:36:23');
INSERT INTO `teacher_question` VALUES (204, 4, 115, '2020-05-03 01:36:23');
INSERT INTO `teacher_question` VALUES (205, 9, 115, '2020-05-03 01:36:23');
INSERT INTO `teacher_question` VALUES (206, 2, 116, '2020-05-03 01:37:35');
INSERT INTO `teacher_question` VALUES (207, 4, 116, '2020-05-03 01:37:35');
INSERT INTO `teacher_question` VALUES (208, 9, 116, '2020-05-03 01:37:35');
INSERT INTO `teacher_question` VALUES (248, 2, 117, '2020-05-04 10:22:07');
INSERT INTO `teacher_question` VALUES (249, 4, 117, '2020-05-04 10:22:07');
INSERT INTO `teacher_question` VALUES (250, 9, 117, '2020-05-04 10:22:07');
INSERT INTO `teacher_question` VALUES (251, 9, 118, '2020-05-04 11:00:53');
INSERT INTO `teacher_question` VALUES (252, 5, 118, '2020-05-04 11:00:53');
INSERT INTO `teacher_question` VALUES (253, 9, 119, '2020-05-06 17:31:58');
INSERT INTO `teacher_question` VALUES (254, 4, 120, '2020-05-11 14:27:35');
INSERT INTO `teacher_question` VALUES (255, 9, 120, '2020-05-11 14:27:35');
INSERT INTO `teacher_question` VALUES (256, 4, 121, '2020-05-11 14:48:22');
INSERT INTO `teacher_question` VALUES (257, 9, 121, '2020-05-11 14:48:22');
INSERT INTO `teacher_question` VALUES (258, 9, 122, '2020-05-11 17:50:09');
INSERT INTO `teacher_question` VALUES (259, 4, 123, '2020-05-11 22:52:23');
INSERT INTO `teacher_question` VALUES (260, 2, 124, '2020-05-22 15:41:58');
INSERT INTO `teacher_question` VALUES (261, 3, 125, '2020-05-22 15:51:40');
INSERT INTO `teacher_question` VALUES (262, 3, 126, '2020-05-22 16:08:15');
INSERT INTO `teacher_question` VALUES (263, 28, 127, '2020-05-22 16:16:38');
INSERT INTO `teacher_question` VALUES (264, 33, 128, '2020-05-22 16:19:28');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `sex` enum('男','女','保密') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '保密' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话号码',
  `classroom_id` int(11) DEFAULT NULL COMMENT '所属班级id',
  `createtime` datetime(0) NOT NULL COMMENT '注册时间',
  `enabled` tinyint(4) NOT NULL COMMENT '账号是否可用，0-》否，1-》是',
  `locked` tinyint(4) NOT NULL COMMENT '账号是否被锁住，0-》否，1-》是',
  `type` tinyint(4) DEFAULT NULL COMMENT '0-》学生，1-》回答问题的老师',
  `self_introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '自我介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-03 15:04:48', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (2, 'tc1', '老师1', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 14:34:40', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (3, 'tc2', '老师2', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 14:42:05', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (4, 'tc3', '老师3', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '女', '1983-07-05', '13581726261', NULL, '2020-03-13 14:42:49', 1, 1, 1, 'xxxxx');
INSERT INTO `user` VALUES (5, 'tc4', '老师4', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 14:44:13', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (6, 'tc5', '老师5', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 14:51:58', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (7, 'tc6', '老师6', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 14:52:28', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (8, 'tc7', '老师7', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 14:53:45', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (9, 'tc8', '老师8', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 14:54:06', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (10, 'st1', '同学1', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 22:35:36', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (11, 'st2', '同学2', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '女', '1992-03-06', NULL, NULL, '2020-03-13 22:36:59', 1, 1, 0, '');
INSERT INTO `user` VALUES (12, 'st3', '同学3', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-13 22:37:27', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (13, 'st4', '同学4', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-03-19 16:09:03', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (27, 'st5', '同学5', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, 1, '2020-04-15 17:41:48', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (28, 'tc9', '老师9', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:39:39', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (29, 'tc10', '老师10', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:41:53', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (30, 'tc11', '老师11', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:43:02', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (31, 'tc12', '老师12', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:44:04', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (32, 'tc13', '老师13', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:44:56', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (33, 'tc14', '老师14', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:45:45', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (34, 'tc15', '老师15', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:50:00', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (35, 'tc16', '老师16', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:50:47', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (36, 'tc17', '老师17', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 10:52:21', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (37, 'tc18', '老师18', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 11:01:00', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (38, 'tc19', '老师19', '$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW', '保密', NULL, NULL, NULL, '2020-05-07 11:04:58', 1, 1, 1, NULL);

-- ----------------------------
-- Table structure for user_collect
-- ----------------------------
DROP TABLE IF EXISTS `user_collect`;
CREATE TABLE `user_collect`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `question_id` int(11) NOT NULL COMMENT '问题id',
  `createtime` datetime(0) NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_collect
-- ----------------------------
INSERT INTO `user_collect` VALUES (13, 4, 114, '2020-05-09 11:57:30');
INSERT INTO `user_collect` VALUES (14, 9, 119, '2020-05-09 15:01:47');
INSERT INTO `user_collect` VALUES (18, 2, 9, '2020-05-29 09:46:03');
INSERT INTO `user_collect` VALUES (21, 2, 2, '2020-05-29 16:43:12');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 1, 2);
INSERT INTO `user_role` VALUES (3, 1, 3);
INSERT INTO `user_role` VALUES (4, 2, 3);
INSERT INTO `user_role` VALUES (5, 3, 3);
INSERT INTO `user_role` VALUES (6, 4, 3);
INSERT INTO `user_role` VALUES (7, 5, 3);
INSERT INTO `user_role` VALUES (8, 6, 3);
INSERT INTO `user_role` VALUES (9, 7, 3);
INSERT INTO `user_role` VALUES (10, 8, 3);
INSERT INTO `user_role` VALUES (11, 9, 3);
INSERT INTO `user_role` VALUES (12, 10, 2);
INSERT INTO `user_role` VALUES (13, 11, 2);
INSERT INTO `user_role` VALUES (14, 12, 2);
INSERT INTO `user_role` VALUES (15, 13, 2);
INSERT INTO `user_role` VALUES (24, 27, 2);
INSERT INTO `user_role` VALUES (25, 28, 2);
INSERT INTO `user_role` VALUES (26, 29, 2);
INSERT INTO `user_role` VALUES (27, 30, 2);
INSERT INTO `user_role` VALUES (28, 31, 2);
INSERT INTO `user_role` VALUES (29, 32, 2);
INSERT INTO `user_role` VALUES (30, 33, 2);
INSERT INTO `user_role` VALUES (31, 34, 2);
INSERT INTO `user_role` VALUES (32, 35, 2);
INSERT INTO `user_role` VALUES (33, 36, 2);
INSERT INTO `user_role` VALUES (34, 37, 2);
INSERT INTO `user_role` VALUES (35, 38, 2);

SET FOREIGN_KEY_CHECKS = 1;

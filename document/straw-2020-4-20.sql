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

 Date: 20/04/2020 16:47:30
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answer
-- ----------------------------
INSERT INTO `answer` VALUES (1, '如果是MySql的话，先确定分库分表的规则，比如按主键取模，实施方案可以有两种：\r\n\r\n停服方案：首先按照分库分表库规则写好一个处理工具类，然后把现有的数据导入相应的库，这种办法比较笨重；\r\n双写机制（可不停服）：在涉及到增删改的地方，加上新的分库分表处理方案，这样在线上跑一段时间后，大部分的活跃数据也就会存在于新表，剩余少部分的数据，可以用上面的导入工具类在非活跃时段进行处理，这样就不用停服了。', 0, 1, '小陈', 13, '2020-03-13 10:41:40');
INSERT INTO `answer` VALUES (2, 'oracle数据库可以使用分区，容量进一步大的话可考虑分表+分区。如果涉及跨分区甚至跨表统计，建议定时将统计数据写入新表中。', 0, 1, '小陈', 13, '2020-03-13 10:52:31');
INSERT INTO `answer` VALUES (3, '看你们用的什么数据库，如果是oracle就好办了，直接使用分区就行了，按照日期分区。目前我们就是这么做的，我们数据量比你的要大多了，每个月都是上亿的数据，所以我们是按照日期分区的。如果是mysql那你就可能要分表，分库了。', 0, 1, '小陈', 13, '2020-03-13 10:52:52');
INSERT INTO `answer` VALUES (4, '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">getBean这个方法是spring内部自己调用的。。。。在spring启动的时候，他会调用refresh方法会触发这个方法，然后跟据beandefinition里的配置的条件，会判断是否需要延迟加载，然后将类加载并实例化，你可以配置延迟加载，根据延迟加载的条件，它可以在你调用这个bean的时候实例化该对象</span><br></p>', 0, 1, '小陈', 14, '2020-03-13 13:01:33');
INSERT INTO `answer` VALUES (5, '<p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h15rJveADyamAAEOO-Kw6X8804.jpg\" data-filename=\"img\" style=\"width: 809px;\"><br></p>', 0, 1, '小陈', 14, '2020-03-13 14:23:51');
INSERT INTO `answer` VALUES (6, '<p>随便写写。。。。。。。。。。。。</p>', 0, 2, '花倩', 14, '2020-03-13 14:36:21');
INSERT INTO `answer` VALUES (7, '<p>随便说说，，，，，，，，，，</p>', 0, 9, '刘苍松', 14, '2020-03-13 14:56:36');
INSERT INTO `answer` VALUES (8, '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">你就把图片存在服务器上指定文件夹就行了。</span><br></p>', 0, 6, '成恒', 15, '2020-03-13 16:16:04');
INSERT INTO `answer` VALUES (9, '<p>随便说点啥.......</p>', 0, 6, '成恒', 14, '2020-03-14 00:55:16');
INSERT INTO `answer` VALUES (10, '<p>答案A</p>', 0, 6, '成恒', 18, '2020-03-16 20:02:17');
INSERT INTO `answer` VALUES (11, '<p>随便写写</p>', 0, 1, 'admin', 14, '2020-03-16 21:44:42');
INSERT INTO `answer` VALUES (12, '<p>随便写两句。。</p>', 0, 11, '李四', 18, '2020-03-16 21:45:22');
INSERT INTO `answer` VALUES (13, '<p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154FGiAY2dJAALaarBrVyA915.png\" data-filename=\"img\" style=\"width: 981px;\"></p>', 0, 1, 'admin', 1, '2020-03-23 09:44:14');
INSERT INTO `answer` VALUES (14, '<p>随便写写。。。</p>', 0, 9, '刘苍松', 28, '2020-03-23 10:01:04');
INSERT INTO `answer` VALUES (15, '<p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154GGyAWBIOAAHTQf0oRLo363.png\" data-filename=\"img\" style=\"width: 903px;\"><br></p>', 0, 9, '刘苍松', 28, '2020-03-23 10:01:22');
INSERT INTO `answer` VALUES (16, '<p>随便写写。。。。。</p>', 0, 9, '刘苍松', 7, '2020-03-23 10:12:41');
INSERT INTO `answer` VALUES (17, '<p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154GyaAN4iSAAHTQf0oRLo183.png\" data-filename=\"img\" style=\"width: 903px;\"><br></p>', 0, 9, '刘苍松', 7, '2020-03-23 10:12:57');
INSERT INTO `answer` VALUES (18, '<p>随便写写</p>', 0, 4, '王克晶', 2, '2020-03-25 11:32:13');
INSERT INTO `answer` VALUES (19, '随便写写', 0, 4, '王克晶', 66, '2020-04-02 16:43:53');
INSERT INTO `answer` VALUES (20, '<p>随便写写</p>', 0, 5, '刘国斌', 70, '2020-04-03 12:32:33');
INSERT INTO `answer` VALUES (21, '<p>随便写两句</p>', 0, 5, '刘国斌', 63, '2020-04-03 13:00:18');
INSERT INTO `answer` VALUES (22, '<p>随便写写</p><p><img src=\"http://localhost:8080/uploadFile/2020/04/03/ac18b72d-c041-4615-b46c-d602835cb8aa.png\" data-filename=\"img\" style=\"width: 1121px;\"><br></p>', 0, 9, '刘苍松', 63, '2020-04-03 14:21:01');
INSERT INTO `answer` VALUES (23, '<p>随便写写</p>', 0, 5, '刘国斌', 62, '2020-04-03 14:34:05');
INSERT INTO `answer` VALUES (24, '<p>随便写写</p>', 0, 5, '刘国斌', 61, '2020-04-03 14:34:16');
INSERT INTO `answer` VALUES (25, '<p>随便写写</p>', 0, 4, '王克晶', 71, '2020-04-03 17:43:52');
INSERT INTO `answer` VALUES (26, '<p>随便写写</p>', 0, 9, '刘苍松', 1, '2020-04-08 11:48:59');
INSERT INTO `answer` VALUES (27, '<p>测试数据<img src=\"http://localhost:8080/uploadFile/2020/04/08/22a50d5e-4279-4b25-825b-067d4208f428.jpg\" data-filename=\"img\" style=\"width: 1088px;\"></p>', 0, 9, '刘苍松', 6, '2020-04-08 11:57:30');
INSERT INTO `answer` VALUES (28, '<p>随便说说......</p>', 0, 4, '王克晶', 84, '2020-04-10 17:15:43');
INSERT INTO `answer` VALUES (29, '<p>测试。。。。</p>', 0, 4, '王克晶', 10, '2020-04-10 17:26:51');
INSERT INTO `answer` VALUES (30, '<p>测试。。。。</p>', 0, 4, '王克晶', 31, '2020-04-17 21:34:40');
INSERT INTO `answer` VALUES (31, '<p>测试222</p>', 0, 4, '王克晶', 31, '2020-04-17 21:36:25');

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
INSERT INTO `classroom` VALUES (1, 'JSD1912', 'JSD1912-537789', 1, '2020-03-31 11:29:26', '2020-04-20 16:45:39');
INSERT INTO `classroom` VALUES (2, 'JSD2001', 'JSD2001-476750', 1, '2020-03-31 13:41:29', '2020-04-20 16:45:39');
INSERT INTO `classroom` VALUES (3, 'JSD2002', 'JSD2002-999843', 1, '2020-03-31 13:41:58', '2020-04-20 16:45:39');
INSERT INTO `classroom` VALUES (4, 'JSD2003', 'JSD2003-749322', 1, '2020-03-31 13:42:23', '2020-04-20 16:45:39');

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
  `status` int(1) NOT NULL COMMENT '状态，0-》未回答，1-》待解决，2-》已解决',
  `page_views` int(11) NOT NULL COMMENT '浏览量',
  `public_status` int(1) NOT NULL COMMENT '该问题是否公开，所有学生都可见，0-》否，1-》是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, '大量的if-eles要怎么优化?', '像这样有大量的if-else做判断，而且后续还会新增，每次新增都要再加一个if-else,有没有大佬来指导一下工作', '小陈', 13, '2020-03-09 14:39:25', 1, 37, 1);
INSERT INTO `question` VALUES (2, 'jdk1.8对sycn做了什么优化？', 'jdk1.8对sycn做了什么优化？', '小明同学', 13, '2020-03-09 15:12:28', 2, 20, 1);
INSERT INTO `question` VALUES (3, 'Eureka的多活请问那节课有的啊?', '双活系统是怎么做的啊?哪里有啊?谢谢', '小明同学', 13, '2020-03-09 23:39:39', 0, 34, 1);
INSERT INTO `question` VALUES (4, '寻求mybatis二级缓存失效的解决方案', '我有一点疑问 ，关于 mybatis二级缓存的，因为二级缓存运用域在相同的namespace上，这时候这个namespace里面做了增删改，二级缓存就失效了，那我们可不可以采用多namespace在代码层面实现读写分离，也就是cqrs，这样是不是就可以避免二级缓存失效的问题', '小明同学', 13, '2020-03-10 12:05:03', 0, 103, 0);
INSERT INTO `question` VALUES (5, 'springIOC容器中的bean在什么时候被实例化和初始化？', '容器中的bean在被使用之前是不是都是没有初始化的？那有没有被实例化呢？还是说被使用之前一直都是以beandefinition的实例保存在容器中，直到getBean方法被调用才会实例化和初始化？', '小明同学', 13, '2020-03-10 12:06:20', 0, 68, 0);
INSERT INTO `question` VALUES (6, 'ES做全文搜索会过滤特殊符号怎么办?', 'boolQuery.should(QueryBuilders.matchPhrasePrefixQuery(s.fieldName(), word).boost(s.boostScore()));\r\n或\r\nboolQuery.should(QueryBuilders.matchPhraseQuery(s.fieldName(), word).boost(s.boostScore()));\r\n\r\n使用这个方法搜索 x2-2 结果搜出来的是x22的结果 - 被屏蔽掉\r\n\r\n使用了转义也没有用 QueryParser.escape(word.toLowerCase())', '小明同学', 13, '2020-03-10 12:08:20', 1, 115, 0);
INSERT INTO `question` VALUES (7, '阿里一道面试题', '题目：找出用户最爱歌曲风格\r\n假设有个用户唱歌的数据结构: Map<String, List> userSongs。 key是用户名，list是用户最近唱歌列表。\r\n有个歌曲风格的数据结构: Map<String, List> songGenres。 key是歌曲风格，list是歌曲列表。\r\n任务是返回一个用户最喜欢的歌曲风格map: Map<String, List>。 key是用户名，list是歌曲风格列表，list中的value是用户听的最多个歌曲对应的歌曲风格。', '小明同学', 13, '2020-03-10 16:57:53', 2, 39, 1);
INSERT INTO `question` VALUES (8, 'JVM判断垃圾对象问题', '判断一个对象是否是垃圾对象的方法有两种：引用计数法和可达性分析法。\r\n请问：这两种方法是怎么使用的？是针对不同的运行时区域有不同的垃圾对象判断还是两者混合使用？', '小明同学', 13, '2020-03-10 16:58:33', 0, 41, 0);
INSERT INTO `question` VALUES (9, 'docker', '为什么jack老师 课堂上，它也是使用的阿里云，在自己电脑上可以访问。我的为什么不行呢？', '小明同学', 13, '2020-03-10 16:59:18', 0, 12, 0);
INSERT INTO `question` VALUES (10, 'java反射', '有没有人清楚JDK1.7和1.8在反射调用实例方法上对参数检查处理上是否有差异？\r\n发现一段代码，对象有个com.edu.bean.A类型的属性type，反射调用setType(com.edu.A)，在1.7环境中运行正常，1.8就报类型不匹配了。\r\n【PS】：先不用管type是com.edu.bean.A类型，为啥setType传入的参数是com.edu.bean.A的问题，希望能从反射原理帮忙解释一下为什么1.7和1.8会出现不同的现象。', '小明同学', 13, '2020-03-10 17:05:12', 1, 12, 0);
INSERT INTO `question` VALUES (11, 'Feign报错feign.FeignException$BadRequest: ....', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &q....', '小明同学', 13, '2020-03-12 17:21:22', 2, 1, 1);
INSERT INTO `question` VALUES (12, 'Zookeeper源码分析中的Leader选举问题 java', '<p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">类文件FastLeaderElection</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">// 缓存收到的票据<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">recvset.put(n.sid, new Vote(n.leader, n.zxid, n.electionEpoch, n.peerEpoch));</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">// Leader选举<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">if (termPredicate(recvset,new Vote(proposedLeader, proposedZxid,logicalclock.get(), proposedEpoch)))</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">// votes表示收到的外部选票的集合<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">// vote表示当前服务器的选票<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">protected boolean termPredicate(<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">HashMap&lt;Long, Vote&gt; votes,<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">Vote vote) {</p><pre style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 16px; font-family: SFMono-Regular, Consolas, &quot;Liberation Mono&quot;, Menlo, Courier, monospace; font-size: 13.6px; overflow-wrap: normal; line-height: 1.45; background-color: rgb(246, 248, 250); border-radius: 3px; color: rgb(36, 41, 46);\"><code style=\"-webkit-tap-highlight-color: transparent; margin: 0px; font-family: SFMono-Regular, Consolas, &quot;Liberation Mono&quot;, Menlo, Courier, monospace; font-size: 13.6px; background-image: initial; background-position: 0px 0px; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; border-radius: 3px; word-break: normal; white-space: pre; border: 0px; display: inline; overflow: visible; line-height: inherit; overflow-wrap: normal;\">HashSet&lt;Long&gt; set = new HashSet&lt;Long&gt;();\r\n\r\n/*\r\n * First make the views consistent. Sometimes peers will have\r\n * different zxids for a server depending on timing.\r\n *\r\n */\r\n// 遍历接收到的所有选票数据\r\nfor (Map.Entry&lt;Long,Vote&gt; entry : votes.entrySet()) {\r\n    //对选票进行归纳，就是把所有选票数据中和当前节点的票据相同的票据进行统计\r\n    if (vote.equals(entry.getValue())){ //对票据进行归纳\r\n        set.add(entry.getKey()); //如果存在2票，set里面是不是有2个？\r\n    }\r\n}\r\n\r\n//对选票进行判断\r\n// 判断当前节点的票数是否是大于一半，默认采用QuorumMaj来实现\r\nreturn self.getQuorumVerifier().containsQuorum(set); //验证\r\n</code></pre><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">}</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\">问题：假如有3台机器的zookeeper集群（server1,server2,server3），那votes的最多存在2票，如果要选出Leader，那么必须所有机器都要统一votes才行，这样不就和\"过半原则不符\"了吗？</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\"><br></p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\"><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h15qAfeAfFDJAAEOO-Kw6X8445.jpg\" data-filename=\"img\" style=\"width: 763px;\"><br></p>', '小明同学', 13, '2020-03-12 17:33:46', 2, 2, 1);
INSERT INTO `question` VALUES (13, '数据库已存在的大数据量的表如何进行分表', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">由于历史原因，系统中有一张表数据量逐年变大，变到了几千万甚至上亿，这些数据不能像日志一样定时归档和删除，求问怎么做分表？</span><br></p>', '小明同学', 13, '2020-03-13 10:51:36', 0, 1, 0);
INSERT INTO `question` VALUES (14, 'springIOC容器中的bean在什么时候被实例化和初始化', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">容器中的bean在被使用之前是不是都是没有初始化的？那有没有被实例化呢？还是说被使用之前一直都是以beandefinition的实例保存在容器中，直到getBean方法被调用才会实例化和初始化？</span><br></p>', '小明同学', 13, '2020-03-13 12:21:16', 1, 5, 0);
INSERT INTO `question` VALUES (15, 'springboot上传图片问题', '<p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">使用springboot 上传图片指定到 resources/public目录下，可以通过浏览器直接访问。<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">如上传的文件为<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">resources/public/a.jpg</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">访问路径 http://host:port/a.jpg</p><p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">然而， springboot打包后是一个jar文件，那么public目录无法写文件，造成上传图片功能不可用，请问使用springboot上传到指定目录后通过应用服务如何访问？</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\">前提，没有图片服务器，是通过 应用服务器访问的图片</p>', '范传奇', 3, '2020-03-13 16:03:12', 1, 4, 0);
INSERT INTO `question` VALUES (16, 'JSON方式实现深克隆', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">不明白为什么Java转成JSON，JSON再转成Java就成深克隆了。。</span></p><p><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h15uTu2AKJ5eAALaarBrVyA489.png\" data-filename=\"img\" style=\"width: 665px;\"><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\"><br></span><br></p>', '李四同学', 11, '2020-03-15 23:51:10', 0, 1, 0);
INSERT INTO `question` VALUES (17, 'curl端口访问不通的问题', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">我这边centos7虚拟机通过curl http://127.0.0.1:5601访问本机端口是正常的,但是通过具体的curl http://具体ip:5601就不行,想请问一下是什么原因</span><img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h15vIJmAJ_DrAAHTQf0oRLo063.png\" data-filename=\"img\" style=\"width: 665px;\"><br></p>', '李四同学', 11, '2020-03-16 14:45:46', 0, 10, 0);
INSERT INTO `question` VALUES (18, '【大厂面试题2020/3/15】', '<p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">关于JSP生命周期的叙述，下列哪些为真?</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\">A、JSP会先解释成Servlet源文件，然后编译成Servlet类文件<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">B、每当用户端运行JSP时，jsp init()方法都会运行一次<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">C、每当用户端运行JSP时，jsp service()方法都会运行一次<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">D、每当用户端运行JSP时，jsp destroy()方法都会运行一次</p>', '李四同学', 11, '2020-03-16 15:20:12', 1, 13, 0);
INSERT INTO `question` VALUES (19, 'dubbo负载均衡问题', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">服务端已限制每个节点的dubbo.protocol.threads限制数为600，尝试过使用各种负载均衡策略，及调整每个节点的负载权重，但并发较高时仍是不能分配均衡，总有几个节点会满负载，不清楚是什么原因造成的，该如何解决？</span><br></p>', '李四同学', 11, '2020-03-19 09:52:40', 0, 1, 0);
INSERT INTO `question` VALUES (20, '数据库已存在的大数据量的表如何进行分表', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">由于历史原因，系统中有一张表数据量逐年变大，变到了几千万甚至上亿，这些数据不能像日志一样定时归档和删除，求问怎么做分表？</span><br></p>', '李四同学', 11, '2020-03-19 12:05:20', 0, 4, 0);
INSERT INTO `question` VALUES (25, '求一个优化的薪酬计算方案', '<p><span style=\"color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">薪酬计算方案：目前很简单，就是根据不同薪酬计算方式归类，然后分步计算后加入到待入库集合中统一插入，但是在计算过程中需要请求多次数据库，因为不同的计算方式都在数据库中保存，哪位大神帮忙提供下方案！！！</span><br></p>', '李四同学', 11, '2020-03-19 14:57:38', 0, 8, 0);
INSERT INTO `question` VALUES (26, 'springboot内置tomcat配置疑问', '<p><span style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px; font-weight: 600; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">spring-boot-autoconfigure 版本为1.5.9 和 2.0.1时，如图所示，很多tomact参数为0，如最大线程maxThreads，在 2.0.7版本中最大线程为200，<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">那么问题来了，我们平时看资料说 springboot内置tomcat默认线程为200，那么在1.5.9 和 2.0.1默认最大线程为0的情况下，如果不显示声明线程池大小的情况下，那么该项目的最大线程是否就为0？大佬们解答一下我的疑问</span><br></p>', '李四同学', 11, '2020-03-19 16:14:17', 0, 14, 0);
INSERT INTO `question` VALUES (27, '面试题', '<p>xxxxxxxxxxxxxx<img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154FA-AfZpxAAHTQf0oRLo407.png\" data-filename=\"img\" style=\"width: 809px;\"></p>', 'admin', 1, '2020-03-23 09:42:45', 0, 1, 0);
INSERT INTO `question` VALUES (28, '面试题？？？？', '<p>xxxxxxxx<img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154F92AMyQhAALaarBrVyA279.png\" data-filename=\"img\" style=\"width: 809px;\"></p>', '小明同学', 13, '2020-03-23 09:58:56', 1, 9, 0);
INSERT INTO `question` VALUES (29, '什么是springcloud???', '<p>xxxxxxxx<img src=\"http://39.97.229.107/group1/M00/00/06/rBF9h154GoWAbVLlAALaarBrVyA395.png\" data-filename=\"img\" style=\"width: 809px;\"></p>', '小明同学', 13, '2020-03-23 10:10:18', 0, 27, 0);
INSERT INTO `question` VALUES (31, '【大厂面试题2020/4/1】', '<p style=\"-webkit-tap-highlight-color: transparent; margin-bottom: 16px; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px;\">下面哪个描述正确? （）</p><p style=\"-webkit-tap-highlight-color: transparent; padding: 0px; color: rgb(36, 41, 46); font-family: &quot;PingFang SC&quot;, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 16px; margin-bottom: 0px !important;\">A、echo $$返回登录shell的PID和echo $？ 返回上一个命令的状态<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">B、echo $$返回上一个命令和echo $的状态？ 返回登录shell的PID<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">C、echo $$和echo $？ 返回一些无意义的整数值<br style=\"-webkit-tap-highlight-color: transparent; margin: 0px; padding: 0px;\">D、所有陈述都是错误的</p>', 'chenhaibao', 23, '2020-04-01 15:02:53', 1, 32, 0);
INSERT INTO `question` VALUES (105, '测试1s', '<p><img src=\"http://localhost:8083/uploadFile/2020/04/20/2f37fe08-6dbe-4c0d-a874-98f45969ac38.jpg\" data-filename=\"img\" style=\"width: 708px;\"><br></p>', '王克晶', 4, '2020-04-20 12:17:29', 0, 0, 0);
INSERT INTO `question` VALUES (106, '测试1s', '<p><img src=\"http://localhost:8083/uploadFile/2020/04/20/2f37fe08-6dbe-4c0d-a874-98f45969ac38.jpg\" data-filename=\"img\" style=\"width: 708px;\"><br></p>', '王克晶', 4, '2020-04-20 12:17:42', 0, 0, 0);

-- ----------------------------
-- Table structure for question_tag
-- ----------------------------
DROP TABLE IF EXISTS `question_tag`;
CREATE TABLE `question_tag`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `question_id` int(20) NOT NULL,
  `tag_id` int(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 190 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-03 15:04:48', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (2, 'huaq', '花倩', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 14:34:40', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (3, 'fancq', '范传奇', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 14:42:05', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (4, 'wangkj', '王克晶', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '女', '1983-07-05', '13581726261', NULL, '2020-03-13 14:42:49', 1, 1, 1, 'xxxxx');
INSERT INTO `user` VALUES (5, 'liugb', '刘国斌', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 14:44:13', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (6, 'chengh', '成恒', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 14:51:58', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (7, 'zhanghl', '张皓岚', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 14:52:28', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (8, 'chengzh', '程祖红', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 14:53:45', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (9, 'liucs', '刘苍松', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 14:54:06', 1, 1, 1, NULL);
INSERT INTO `user` VALUES (10, 'zhangs', '张三同学', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 22:35:36', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (11, 'lis', '李四同学', '$2a$10$vF9GxQRKzGYqcu6hst0RhO.pX8QidM7AvTxPHM0b1Z8DzAKBihquS', '女', '1992-03-06', NULL, NULL, '2020-03-13 22:36:59', 1, 1, 0, '');
INSERT INTO `user` VALUES (12, 'wangw', '王五同学', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-13 22:37:27', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (13, 'xiaom', '小明同学', '$2a$10$oN4isHfJO/9aiR2MaGpnTuauq2FuyXygAg/LR5irP5XGdBSGnuHwS', '保密', NULL, NULL, NULL, '2020-03-19 16:09:03', 1, 1, 0, NULL);
INSERT INTO `user` VALUES (27, '18501927843', 'chenhaibao', '$2a$10$h4rJhAk5Ux22tY5fZTTIMOlEf.HBbZ.hjk6rfdB6ojiSuXRoCPIpu', '保密', NULL, NULL, 1, '2020-04-15 17:41:48', 1, 1, 0, NULL);

-- ----------------------------
-- Table structure for user_question
-- ----------------------------
DROP TABLE IF EXISTS `user_question`;
CREATE TABLE `user_question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `createtime` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 182 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_question
-- ----------------------------
INSERT INTO `user_question` VALUES (1, 3, 1, '2020-04-01 12:48:50');
INSERT INTO `user_question` VALUES (2, 3, 2, '2020-04-01 12:48:58');
INSERT INTO `user_question` VALUES (3, 3, 3, '2020-04-01 12:49:05');
INSERT INTO `user_question` VALUES (4, 3, 4, '2020-04-01 12:49:10');
INSERT INTO `user_question` VALUES (5, 3, 5, '2020-04-01 12:49:21');
INSERT INTO `user_question` VALUES (6, 3, 6, '2020-04-01 12:49:27');
INSERT INTO `user_question` VALUES (7, 3, 7, '2020-04-01 12:49:35');
INSERT INTO `user_question` VALUES (8, 3, 8, '2020-04-01 12:49:42');
INSERT INTO `user_question` VALUES (9, 3, 9, '2020-04-01 12:49:49');
INSERT INTO `user_question` VALUES (10, 3, 10, '2020-04-01 12:49:56');
INSERT INTO `user_question` VALUES (11, 3, 11, '2020-04-01 12:50:03');
INSERT INTO `user_question` VALUES (12, 3, 12, '2020-04-01 12:50:13');
INSERT INTO `user_question` VALUES (91, 4, 70, '2020-04-03 11:18:20');
INSERT INTO `user_question` VALUES (92, 8, 70, '2020-04-03 12:58:44');
INSERT INTO `user_question` VALUES (93, 8, 63, '2020-04-03 14:19:56');
INSERT INTO `user_question` VALUES (94, 8, 47, '2020-04-03 15:45:59');
INSERT INTO `user_question` VALUES (95, 8, 46, '2020-04-03 15:45:59');
INSERT INTO `user_question` VALUES (96, 8, 50, '2020-04-03 15:47:29');
INSERT INTO `user_question` VALUES (97, 8, 43, '2020-04-03 15:48:16');
INSERT INTO `user_question` VALUES (98, 8, 42, '2020-04-03 15:48:16');
INSERT INTO `user_question` VALUES (99, 8, 53, '2020-04-03 15:49:25');
INSERT INTO `user_question` VALUES (100, 8, 52, '2020-04-03 15:49:25');
INSERT INTO `user_question` VALUES (101, 3, 71, '2020-04-03 17:43:11');
INSERT INTO `user_question` VALUES (102, 4, 71, '2020-04-03 17:43:11');
INSERT INTO `user_question` VALUES (103, 8, 12, '2020-04-03 17:44:47');
INSERT INTO `user_question` VALUES (107, 3, 74, '2020-04-08 10:36:45');
INSERT INTO `user_question` VALUES (108, 4, 74, '2020-04-08 10:36:45');
INSERT INTO `user_question` VALUES (109, 2, 75, '2020-04-08 15:48:28');
INSERT INTO `user_question` VALUES (110, 4, 75, '2020-04-08 15:48:28');
INSERT INTO `user_question` VALUES (111, 1, 10, '2020-04-09 17:18:02');
INSERT INTO `user_question` VALUES (112, 8, 10, '2020-04-09 17:22:50');
INSERT INTO `user_question` VALUES (113, 7, 10, '2020-04-09 17:30:18');
INSERT INTO `user_question` VALUES (114, 2, 9, '2020-04-09 17:32:19');
INSERT INTO `user_question` VALUES (115, 2, 4, '2020-04-09 17:33:11');
INSERT INTO `user_question` VALUES (116, 2, 10, '2020-04-09 19:09:11');
INSERT INTO `user_question` VALUES (117, 4, 7, '2020-04-09 19:22:39');
INSERT INTO `user_question` VALUES (178, 4, 105, '2020-04-20 12:17:29');
INSERT INTO `user_question` VALUES (179, 8, 105, '2020-04-20 12:17:29');
INSERT INTO `user_question` VALUES (180, 4, 106, '2020-04-20 12:17:42');
INSERT INTO `user_question` VALUES (181, 8, 106, '2020-04-20 12:17:42');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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

SET FOREIGN_KEY_CHECKS = 1;

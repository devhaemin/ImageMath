
/**
 * @apiDefine tutor User access only
 * This optional description belong to to the group tutor
 *
 */

/**
 * @apiDefine student User access only
 * This optional description belong to to the group student
 *
 */

/**
 * @apiDefine normal User access only
 * This optional description belong to to the group normal
 *
 */
/**
 * @apiDefine AcademyInfo
 * @apiParam {Int} academySeq 학원 번호
 * @apiParam {String} academyName 학원 이름
 *
 */
/**
 * @apiDefine UserInfo
 * @apiSuccess (200) {Int} userSeq
 * @apiSuccess (200) {String} name
 * @apiSuccess (200) {String} birthday
 * @apiSuccess (200) {String} email
 * @apiSuccess (200) {String} password
 * @apiSuccess (200) {String} accessToken
 * @apiSuccess (200) {String} fcmToken
 * @apiSuccess (200) {String} gender
 * @apiSuccess (200) {String} salt
 * @apiSuccess (200) {String} userType
 * @apiSuccess (200) {String} phone
 * @apiSuccess (200) {String} studentCode
 * @apiSuccess (200) {String} schoolName
 * @apiSuccess (200) {Int} registerTime
 */

/**
 * @apiDefine LectureInfo
 * @apiParam {String} name 수업 이름
 * @apiParam {String} weekDay 수업 진행 요일
 * @apiParam {String} time 수업 진행 시간
 * @apiParam {String} totalDate 수업 진행 총 기간
 * @apiParam {String} week 수업 진행 주
 * @apiParam {String} studentNum 수업 학생 수
 */

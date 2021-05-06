
Parse.Cloud.define("adminDeleteUser", async (req) => {
	const query = new Parse.Query(Parse.User);
	query.equalTo("objectId", req.params.id);
	const results = await query.find();
	const user = results[0];
	return user.destroy({useMasterKey:true});
});
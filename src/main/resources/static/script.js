var templates = {};

function getTemplate(name, callback) {
	if(name in templates) {
		callback(templates[name]);
	} else {
		$.get({
			url: "/templates/" + name + ".html",
			dataType: "text"
		}).done(function(data) {
			Mustache.parse(data);
			templates[name] = data;
			callback(templates[name]);
		});
	}
}

function route(r, t) {
}

GoldenRoute.addRoute("/thread", function(params, query, callback) {
	$.get("/api/thread").done(function(data) {
		getTemplate("thread", function(template) {
			$("main").html(Mustache.render(template, data.data));
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/category", function(params, query, callback) {
	$.get("/api/category").done(function(data) {
		getTemplate("category", function(template) {
			$("main").html(Mustache.render(template, data.data));
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/user", function(params, query, callback) {
	$.get("/api/user").done(function(data) {
		getTemplate("user", function(template) {
			$("main").html(Mustache.render(template, data.data));
			callback("titolo");
		});
	});
});

$(function() {
	GoldenRoute.start();
});

var templates = {};
var session = null;

function getCookie(name) {
	var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
	if(match) {
		return match[2];
	}
}

function parseJwt(token) {
    var base64Url = token.split(".")[1];
    var base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    var jsonPayload = decodeURIComponent(atob(base64).split("").map(function(c) {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(""));
    return JSON.parse(jsonPayload);
};

if(auth = getCookie("auth")) {
	session = parseJwt(auth).user;
}

function updateMenu(callback) {
	getTemplate("menu", function(template) {
		$("#menu").html(Mustache.render(template, {
			session: session
		}));
		callback();
	});
}

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

GoldenRoute.addRoute("/thread/:id", function(params, query, callback) {
	$.get("/api/threads/" + params.id).done(function(data) {
		data = data.data;
		getTemplate("thread", function(template) {
			data.session = session;
			$("main").html(Mustache.render(template, data));
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/category/:id", function(params, query, callback) {
	$.get("/api/categories/" + params.id).done(function(data) {
		getTemplate("category", function(template) {
			$("main").html(Mustache.render(template, data.data));
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/user/:id", function(params, query, callback) {
	$.get("/api/users/" + params.id).done(function(data) {
		getTemplate("user", function(template) {
			$("main").html(Mustache.render(template, data.data));
			callback("titolo");
		});
	});
});

GoldenRoute.addRoute("/login", function(params, query, callback) {
	getTemplate("login", function(template) {
		$("main").html(Mustache.render(template, { error: "error" in query }));
		callback("titolo");
	});
});

GoldenRoute.addRoute("/new", function(params, query, callback) {
	getTemplate("new_thread", function(template) {
		$("main").html(Mustache.render(template, {}));
		callback("titolo");
	});
});

GoldenRoute.addRoute("/user", function(params, query, callback) {
	$.get("/api/user/details").done(function(data) {
		getTemplate("user_details", function(template) {
			$("main").html(Mustache.render(template, data.data));
			callback("titolo");
		});
	});
});

$(function() {
	GoldenRoute.start();
});

function onNewPostSubmit(form) {
	form = $(form);
	var thread_id = parseInt(form.attr("action").split("/")[2]);
	var text = form.find("[name='text']").val();
	$.post({
		url: "/api/threads/" + thread_id + "/posts",
		data: JSON.stringify({
			thread_id: parseInt(thread_id),
			text: text
		}),
		contentType: "application/json; charset=utf-8"
	}).done(function() {
		GoldenRoute.routeTo("/thread/" + thread_id);
	});
	return false;
}

function onNewThreadSubmit(form) {
	form = $(form);
	var category_id = form.find("[name='category_id']").val();
	var text = form.find("[name='text']").val();
	var title = form.find("[name='title']").val();
	$.post({
		url: "/api/threads",
		data: JSON.stringify({
			category_id: parseInt(category_id),
			title: title,
			text: text
		}),
		contentType: "application/json; charset=utf-8"
	}).done(function(data) {
		data = data.data;
		GoldenRoute.routeTo("/thread/" + data.thread.id);
	});
	return false;
}

function onLogin(form) {
	form = $(form);
	var username = form.find("[name='username']").val();
	var password = form.find("[name='password']").val();
	$.post({
		url: "/api/login",
		data: JSON.stringify({
			username: username,
			password: password
		}),
		contentType: "application/json; charset=utf-8"
	}).done(function(data) {
		if(data.success) {
			data = data.data;
			session = data.user;
			updateMenu(function() {
				GoldenRoute.routeTo("/");
			});
		} else {
			getTemplate("login", function(template) {
				$("main").html(Mustache.render(template, { error: true }));
				callback("titolo");
			});
		}
	});
	return false;
}

function onUserDetailsSubmit(form) {
	form = $(form);
	var description = form.find("[name='description']").val();
	$.post({
		url: "/api/user/details",
		data: JSON.stringify({
			description: description
		}),
		contentType: "application/json; charset=utf-8"
	}).done(function(data) {
		data = data.data;
		GoldenRoute.routeTo("/user");
	});
	return false;
}

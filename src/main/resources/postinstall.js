var sys = require("sys")
var exec = require("child_process").exec;

function puts(error, stdout, stderr) {
	sys.puts(stdout);
}

var os = require('os');

if(os.type() == 'Windows_NT') {
	exec("npm run postinstall-windows", puts);
} else {
	exec("npm run postinstall-default", puts);
}

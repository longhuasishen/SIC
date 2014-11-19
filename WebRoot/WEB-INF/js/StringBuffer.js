/**
 * StringBuffer by fantaros , yanluhan
 */

/**
 * 运行期的单例对象
 */
var $B = new StringBuffer();
/**
 * JSON.stringify
 */
JSON = {
	stringify: function (vContent) {
	  if (vContent instanceof Object) {
		var sOutput = "";
		if (vContent.constructor === Array) {
		  for (var nId = 0; nId < vContent.length; sOutput += this.stringify(vContent[nId]) + ",", nId++);
		  return "[" + sOutput.substr(0, sOutput.length - 1) + "]";
		}
		if (vContent.toString !== Object.prototype.toString) { return "\"" + vContent.toString().replace(/"/g, "\\$&") + "\""; }
		for (var sProp in vContent) { sOutput += "\"" + sProp.replace(/"/g, "\\$&") + "\":" + this.stringify(vContent[sProp]) + ","; }
		return "{" + sOutput.substr(0, sOutput.length - 1) + "}";
	  }
	  return typeof vContent === "string" ? "\"" + vContent.replace(/"/g, "\\$&") + "\"" : String(vContent);
	},
	jsonify : function (jsonstr) {
		if(typeof(jsonstr)!="undefined"){
			var buffer = new StringBuffer();
			buffer.$("(function(){return ").$(jsonstr).$(";})();");
			try{
				return eval(buffer.toString());
			} catch(e){
				return null;
			}
		}
	}
};

function StringBuffer(str) {
	this.buffer = [];
	if(str!=null){
		this.append(str);
	}
}
/**
 * 清空数组(比length=0 效率要高)
 */
StringBuffer.prototype.clear = function(){
	this.buffer = [];
	return this;
}
/**
 * 清空数组别名
 */
StringBuffer.prototype.$c = function(){
	this.buffer = [];
	return this;
}
/**
 * 缓存数组
 */
StringBuffer.prototype.buffer;
/**
 * 取 str 的值 加入buffer
 * @param str
 * @returns {StringBuffer}
 */
StringBuffer.prototype.append = function(str) {
	if(str!=null && str.toString instanceof Function){
		this.buffer.push(str.toString());
	}
	return this;
};
/**
 * append方法的别名, 取 str 的值 加入buffer
 * @param str
 * @returns {StringBuffer}
 */
StringBuffer.prototype.$ = function(str) {
	if(str!=null && str.toString instanceof Function){
		this.buffer.push(str.toString());
	}
	return this;
};
/**
 * toString方法的别名
 * @returns
 */
StringBuffer.prototype._ = function() {
	if(this.buffer == null || this.buffer.length==0){
		return "";
	}
	return this.buffer.join("");
};
/**
 * toString方法的别名
 * @returns
 */
StringBuffer.prototype.s = function() {
	if(this.buffer == null || this.buffer.length==0){
		return "";
	}
	return this.buffer.join("");
};
/**
 * toString方法的别名
 * @returns
 */
StringBuffer.prototype.$s = function() {
	if(this.buffer == null || this.buffer.length==0){
		return "";
	}
	return this.buffer.join("");
};
/**
 * toString方法的别名
 * @returns
 */
StringBuffer.prototype.str = function() {
	if(this.buffer == null || this.buffer.length==0){
		return "";
	}
	return this.buffer.join("");
};
/**
 * 变会值类型只读字符串
 * @returns
 */
StringBuffer.prototype.toString = function() {
	if(this.buffer == null || this.buffer.length==0){
		return "";
	}
	return this.buffer.join("");
};

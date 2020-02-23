(function() {
	if ( (typeof QRCode) === "function" ) {
		QRCode.prototype.makeIcon = function(src, _size) {
			var size = _size ? _size : 30;
			var options = this._htOption;
			var width = options.width;
			var height = options.height;
			
			var logoDiv = document.createElement("div");
			logoDiv.style.border = "3px solid #ADADAD";
			logoDiv.style.borderRadius = "10px";
			logoDiv.style.width = logoDiv.style.height = size + "px";
			logoDiv.style.position = "absolute";
			logoDiv.style.left = (width - size) / 2 + "px";
			logoDiv.style.top = (height - size) / 2 + "px";
			logoDiv.style.backgroundColor = "#fff";
			
			var logoImg = document.createElement("img");
			logoImg.style.width = logoImg.style.height = size + "px";
			logoImg.src = src;
			
			logoDiv.appendChild(logoImg);
			
			this._el.style.position = "relative";
			this._el.appendChild(logoDiv);
		};
	}
})();
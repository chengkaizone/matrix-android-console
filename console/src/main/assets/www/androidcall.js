"use strict";
console.log("Loading call");
var BASE_URL = "https://matrix.org";
var TOKEN = "TOKEN";
var USER_ID = "USER_ID";
var ROOM_ID = "ROOM_ID";
var mxCall;

// debug tools
function showToast(toast) {
     Android.showToast(toast);
}

function androidLog2(message) {
    Android.wlog(message);
}

// initializers
function getAccessToken() {
    return Android.wgetAccessToken();
}

function getRoomId() {
    return Android.wgetRoomId();
}

function getUserId() {
    return Android.wgetUserId();
}

// call when the webview is loaded
function onLoaded() {
    return Android.wOnLoaded();
}

// start call methods
function placeVoiceCall() {
	mxCall.placeVoiceCall();
}

function placeVideoCall() {
	mxCall.placeVideoCall(
		document.getElementById("remote"),
		document.getElementById("self")
	);
}

function initWithInvite(callId, msg) {
	mxCall.localVideoElement = document.getElementById("self");
	mxCall.remoteVideoElement = document.getElementById("remote");
	mxCall.callId = callId;
    mxCall._initWithInvite(msg);
}

// receive candidates
function gotRemoteCandidate(candidate) {
    mxCall._gotRemoteIceCandidate(candidate);
}

// the user accept the call
function answerCall() {
    mxCall.answer();
}

// the callee accepts the call
function receivedAnswer(msg) {
    mxCall._receivedAnswer(msg);
}

// hangup methods
function onHangupReceived(msg) {
    mxCall._onHangupReceived(msg);
}

function hangup() {
	mxCall.hangup("", false);
}

window.onload = function() {
	TOKEN = getAccessToken();
	USER_ID = getUserId();
	ROOM_ID = getRoomId();

	mxCall = createNewMatrixCall(ROOM_ID);
	onLoaded();
};

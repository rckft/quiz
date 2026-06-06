package dev.rckft.notificationservice.notification.processing.email;

public record EmailSendingResponse(String responseCode, String id, String errors) { }

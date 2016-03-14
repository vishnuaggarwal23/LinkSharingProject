package com.ttnd.linksharing

import dto.EmailDTO
import grails.transaction.Transactional
import org.springframework.web.context.request.RequestContextHolder
import util.Linksharing
import util.PasswordGenerator

@Transactional
class EmailService {

    def mailService
    def userService
    def groovyPageRenderer
    def grailsApplication
    def messageSource

    def sendMail(EmailDTO emailDTO) {
        mailService.sendMail {
            to emailDTO.to.toArray()
            subject emailDTO.subject
            if (emailDTO.content) {
                html emailDTO.content
            } else {
                body(view: emailDTO.view, model: emailDTO.model)
            }
        }
    }

    def invite(Long id, String email) {
        Topic topic = Topic.get(id)
        User user = User.findByEmail(email)
        if (topic && user) {
            def session = RequestContextHolder.currentRequestAttributes().getSession()
            EmailDTO emailDTO = new EmailDTO(
                    to: [email],
                    subject: messageSource.getMessage("com.ttnd.linksharing.dto.EmailDTO.topic.invitation.subject",
                            [].toArray(), Locale.default),
                    content: groovyPageRenderer.render(template: '/topic/invite', model: [userName : session.user.firstName,
                                                                                          topicName: topic.name,
                                                                                          email    : email,
                                                                                          topicId  : topic.id,
                                                                                          base     : grailsApplication.config.grails.serverBaseURL])
            )
            if (sendMail(emailDTO)) {
                return true
            }
        }
        return false
    }

    def forgotPassword(String email) {
        User user = User.findByEmail(email)
        if (user && Linksharing.isUserActive(user)) {
            String newPassword = PasswordGenerator.getRandomPassword()
            if (newPassword) {
                EmailDTO emailDTO = new EmailDTO(
                        to: [email],
                        subject: messageSource.getMessage("com.ttnd.linksharing.dto.EmailDTO.forgot.password.subject", [].toArray(), Locale.default),
                        content: groovyPageRenderer.render(template: '/login/password', model: [userName   : user.userName,
                                                                                                newPassword: newPassword])
                )
                if (sendMail(emailDTO)) {
                    user.password = newPassword
                    if (user.save(flush: true)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    def sendUnReadResourcesMail(User user, List<Resource> unreadResources) {
        EmailDTO emailDTO = new EmailDTO(
                to: [user.email],
                subject: messageSource.getMessage("com.tothenew.co.dto.EmailDTO.unread.subject", [].toArray(), Locale
                        .default),
                content: groovyPageRenderer.render(template: '/resource/unreadResouces', model: [user           : user,
                                                                                                 unreadResources: unreadResources])
        )
        sendMail(emailDTO)
    }
}

package com.ttnd.linksharing

import dto.EmailDTO
import grails.transaction.Transactional
import org.springframework.web.context.request.RequestContextHolder
import util.PasswordGenerator

@Transactional
class EmailService {

    def mailService
    def userService
    def groovyPageRenderer
    def grailsLinkGenerator
    def grailsApplication

    def serviceMethod() {

    }

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
            EmailDTO emailDTO = new EmailDTO()
            emailDTO.to = []
            emailDTO.to.add(email)
            emailDTO.subject = "Topic Invitation"
            emailDTO.content = groovyPageRenderer.render(template: '/topic/invite', model: [userName: session.user
                    .firstName, topicName: topic.name, email: email, topicId: topic.id, base: grailsApplication.config
                    .grails.serverBaseURL])
            if(sendMail(emailDTO)){
                return true
            }
        }
        return false
    }

    def forgotPassword(String email) {
        User user = User.findByEmail(email)
        if (user && userService.isActive(user.id)) {
            String newPassword = PasswordGenerator.getRandomPassword()
            if (newPassword) {
                EmailDTO emailDTO = new EmailDTO()
                emailDTO.to = []
                emailDTO.to.add(email)
                emailDTO.subject = "Forgot Password Mail"
                emailDTO.content = groovyPageRenderer.render(template: '/login/password', model: [userName: user
                        .userName, newPassword                                                            : newPassword])
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
}

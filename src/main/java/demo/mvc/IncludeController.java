package demo.mvc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Petar Tahchiev
 * @since 0.6
 */
@Controller
public class IncludeController {

    private final static Logger LOG = LogManager.getLogger(IncludeController.class);

    @Resource(name = "sessionRepository")
    private SessionRepository<ExpiringSession> sessionRepository;

    @Resource(name = "sessionStrategy")
    private HttpSessionStrategy sessionStrategy;

    @RequestMapping(value = "/include", method = RequestMethod.GET)
    public String home(final Model model) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        final String sessionIds = sessionStrategy.getRequestedSessionId(request);

        if (sessionIds != null) {
            final ExpiringSession session = sessionRepository.getSession(sessionIds);
            if (session != null) {
                LOG.error(session.getAttributeNames().size());
                model.addAttribute("session", session);
            }
        }

        return "include";
    }
}

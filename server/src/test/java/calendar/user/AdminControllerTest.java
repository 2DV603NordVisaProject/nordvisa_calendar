package calendar.user;

import calendar.user.dto.RegistrationDecisionDTO;
import calendar.user.dto.UserDTO;
import calendar.user.dto.UserIdDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class AdminControllerTest
 *
 * TODO: Add Comments
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {
    @Mock
    private UserDAO dao;
    @Mock
    private CurrentUser currentUser;
    @Mock
    private Email email;

    @InjectMocks
    private AdminController sut;

    @Test
    public void makeUserValid() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);
        UserIdDTO dtoMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddress()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(dtoMock.getId()).thenReturn("1");
        when(actorMock.canChangeRoleTo(targetMock, "USER")).thenReturn(true);

        try {
            sut.makeUser(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(targetMock, times(1)).setRole("USER");
        verify(dao, times(1)).update(targetMock);
    }

    @Test
    public void makeUserInvalid() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);
        UserIdDTO dtoMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddress()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(dtoMock.getId()).thenReturn("1");
        when(actorMock.canChangeRoleTo(targetMock, "USER")).thenReturn(false);

        try {
            sut.makeUser(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(targetMock, never()).setRole("USER");
        verify(dao, never()).update(targetMock);
    }

    @Test
    public void makeAdminValid() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);
        UserIdDTO dtoMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddress()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(dtoMock.getId()).thenReturn("1");
        when(actorMock.canChangeRoleTo(targetMock, "ADMIN")).thenReturn(true);

        try {
            sut.makeAdmin(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(targetMock, times(1)).setRole("ADMIN");
        verify(dao, times(1)).update(targetMock);
    }

    @Test
    public void makeAdminInvalid() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);
        UserIdDTO dtoMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddress()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(dtoMock.getId()).thenReturn("1");
        when(actorMock.canChangeRoleTo(targetMock, "ADMIN")).thenReturn(false);

        try {
            sut.makeAdmin(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(targetMock, never()).setRole(anyString());
        verify(dao, never()).update(any(User.class));
    }

    // TODO: Update using createUserMock and createOrganizationMock
    @Test
    public void getPendingRegistrations() {
        String adminEmail = "test@test.com";

        User admin = mock(User.class);
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        Organization adminOrg = mock(Organization.class);

        ArrayList<User> pendingReg = new ArrayList<>();
        pendingReg.add(user1);
        pendingReg.add(user2);

        Organization org1 = mock(Organization.class);
        when(user1.getId()).thenReturn("user1_id");
        when(user1.getEmail()).thenReturn("user1@test.com");
        when(user1.getRole()).thenReturn("USER");
        when(user1.getCreatedAt()).thenReturn(100L);
        when(user1.getUpdatedAt()).thenReturn(101L);
        when(user1.getOrganization()).thenReturn(org1);

        Organization org2 = mock(Organization.class);
        when(user2.getId()).thenReturn("user2_id");
        when(user2.getEmail()).thenReturn("user2@test.com");
        when(user2.getRole()).thenReturn("ADMIN");
        when(user2.getCreatedAt()).thenReturn(200L);
        when(user2.getUpdatedAt()).thenReturn(202L);
        when(user2.getOrganization()).thenReturn(org2);

        when(currentUser.getEmailAddress()).thenReturn(adminEmail);
        when(dao.getUserByEmail(adminEmail)).thenReturn(admin);
        when(admin.getOrganization()).thenReturn(adminOrg);
        when(adminOrg.getName()).thenReturn("adminOrg");
        when(dao.getPendingRegistrations(adminOrg.getName())).thenReturn(pendingReg);

        ArrayList<UserDTO> results = sut.getPendingRegistrations();

        assertEquals(2, results.size());

        assertEquals(user1.getId(), results.get(0).getId());
        assertEquals(user1.getEmail(), results.get(0).getEmail());
        assertEquals(user1.getRole(), results.get(0).getRole());
        assertEquals(user1.getCreatedAt(), results.get(0).getCreatedAt());
        assertEquals(user1.getUpdatedAt(), results.get(0).getUpdatedAt());
        assertEquals(user1.getUpdatedAt(), results.get(0).getUpdatedAt());
        assertEquals(user1.getOrganization(), results.get(0).getOrganization());

        assertEquals(user2.getId(), results.get(1).getId());
        assertEquals(user2.getEmail(), results.get(1).getEmail());
        assertEquals(user2.getRole(), results.get(1).getRole());
        assertEquals(user2.getCreatedAt(), results.get(1).getCreatedAt());
        assertEquals(user2.getUpdatedAt(), results.get(1).getUpdatedAt());
        assertEquals(user2.getUpdatedAt(), results.get(1).getUpdatedAt());
        assertEquals(user2.getOrganization(), results.get(1).getOrganization());
    }

    @Test
    public void registrationDecisionApprovedRegistration() {
        RegistrationDecisionDTO dto = mock(RegistrationDecisionDTO.class);
        User user = mock(User.class);
        Organization userOrg = mock(Organization.class);

        when(dto.getId()).thenReturn("user_id");
        when(dto.isApproved()).thenReturn(true);
        when(dao.getUserById(dto.getId())).thenReturn(user);
        when(user.getEmailChange()).thenReturn("test@test.com");
        when(user.getOrganization()).thenReturn(userOrg);
        when(userOrg.getChangePending()).thenReturn("");
        when(userOrg.isApproved()).thenReturn(false);

        sut.registrationDecision(dto);

        verify(userOrg, times(1)).setApproved(true);
        verify(userOrg, times(1)).setName(userOrg.getChangePending());
        verify(userOrg, times(1)).setChangePending("_");
        verify(dao, times(1)).update(user);
        verify(email, times(1)).sendSuccessEmail(user.getEmail(),
                "registration");
    }

    @Test
    public void registrationDecisionApprovedOrganizationChange() {
        RegistrationDecisionDTO dto = mock(RegistrationDecisionDTO.class);
        User user = mock(User.class);
        Organization userOrg = mock(Organization.class);

        when(dto.getId()).thenReturn("user_id");
        when(dto.isApproved()).thenReturn(true);
        when(dao.getUserById(dto.getId())).thenReturn(user);
        when(user.getEmailChange()).thenReturn("test@test.com");
        when(user.getOrganization()).thenReturn(userOrg);
        when(userOrg.getChangePending()).thenReturn("");
        when(userOrg.isApproved()).thenReturn(true);

        sut.registrationDecision(dto);

        verify(userOrg, times(1)).setApproved(true);
        verify(userOrg, times(1)).setName(userOrg.getChangePending());
        verify(userOrg, times(1)).setChangePending("_");
        verify(dao, times(1)).update(user);
        verify(email, times(1)).sendSuccessEmail(user.getEmail(),
                "organization change");
    }

    @Test
    public void registrationDecisionDeniedRegistration() {
        RegistrationDecisionDTO dto = mock(RegistrationDecisionDTO.class);
        User user = mock(User.class);
        Organization userOrg = mock(Organization.class);

        when(dto.getId()).thenReturn("user_id");
        when(dto.isApproved()).thenReturn(false);
        when(dao.getUserById(dto.getId())).thenReturn(user);
        when(user.getEmailChange()).thenReturn("test@test.com");
        when(user.getOrganization()).thenReturn(userOrg);
        when(userOrg.getChangePending()).thenReturn("");
        when(userOrg.isApproved()).thenReturn(false);

        sut.registrationDecision(dto);

        verify(userOrg, never()).setApproved(anyBoolean());
        verify(userOrg, never()).setName(anyString());
        verify(userOrg, never()).setChangePending("_");
        verify(dao, never()).update(user);
        verify(dao, times(1)).delete(dto.getId());
        verify(email, times(1)).sendDenialEmail(user.getEmail(),
                "registration");
    }

    @Test
    public void registrationDecisionDeniedOrganizationChange() {
        RegistrationDecisionDTO dto = mock(RegistrationDecisionDTO.class);
        User user = mock(User.class);
        Organization userOrg = mock(Organization.class);

        when(dto.getId()).thenReturn("user_id");
        when(dto.isApproved()).thenReturn(false);
        when(dao.getUserById(dto.getId())).thenReturn(user);
        when(user.getEmailChange()).thenReturn("test@test.com");
        when(user.getOrganization()).thenReturn(userOrg);
        when(userOrg.getChangePending()).thenReturn("");
        when(userOrg.isApproved()).thenReturn(true);

        sut.registrationDecision(dto);

        verify(userOrg, never()).setApproved(true);
        verify(userOrg, never()).setName(userOrg.getChangePending());
        verify(userOrg, times(1)).setChangePending("_");
        verify(dao, times(1)).update(user);
        verify(email, times(1)).sendDenialEmail(user.getEmail(),
                "organization change");
    }

    @Test
    public void getManageableUsersAsGlobalAdmin() {
        Organization adminOrg = createOrganizationMock("my_org", true, "_");
        User admin = createUserMock("1", "admin@test.com", "ADMIN", 111, 112, adminOrg);

        Organization manageableUserOrg = createOrganizationMock("my_org", true, "_");
        User manageableUser = createUserMock("2", "user1@test.com", "USER", 222, 223, manageableUserOrg);

        Organization nonManageableUserOrg = createOrganizationMock("", true, "_");
        User nonManageableUser = createUserMock("3", "user2@test.com", "USER", 333, 334, nonManageableUserOrg);

        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(admin);
        allUsers.add(manageableUser);
        allUsers.add(nonManageableUser);

        when(currentUser.getEmailAddress()).thenReturn("admin@test.com");
        when(dao.getUserByEmail("admin@test.com")).thenReturn(admin);
        when(dao.getAllUsers()).thenReturn(allUsers);

        when(admin.canManage(admin)).thenReturn(true);
        when(admin.canManage(manageableUser)).thenReturn(true);
        when(admin.canManage(nonManageableUser)).thenReturn(false);

        ArrayList<UserDTO> results = sut.getManageableUsers();

        assertEquals(2, results.size());

        assertEquals(admin.getId(), results.get(0).getId());
        assertEquals(admin.getEmail(), results.get(0).getEmail());
        assertEquals(admin.getRole(), results.get(0).getRole());
        assertEquals(admin.getCreatedAt(), results.get(0).getCreatedAt());
        assertEquals(admin.getUpdatedAt(), results.get(0).getUpdatedAt());
        assertEquals(admin.getOrganization(), results.get(0).getOrganization());

        assertEquals(manageableUser.getId(), results.get(1).getId());
        assertEquals(manageableUser.getEmail(), results.get(1).getEmail());
        assertEquals(manageableUser.getRole(), results.get(1).getRole());
        assertEquals(manageableUser.getCreatedAt(), results.get(1).getCreatedAt());
        assertEquals(manageableUser.getUpdatedAt(), results.get(1).getUpdatedAt());
        assertEquals(manageableUser.getOrganization(), results.get(1).getOrganization());
    }

    private User createUserMock(String id, String email, String role, long createdAt,
                                long updatedAt, Organization organization) {
        User user = mock(User.class);

        when(user.getId()).thenReturn(id);
        when(user.getEmailChange()).thenReturn(email);
        when(user.getRole()).thenReturn(role);
        when(user.getCreatedAt()).thenReturn(createdAt);
        when(user.getUpdatedAt()).thenReturn(updatedAt);
        when(user.getOrganization()).thenReturn(organization);

        return user;
    }

    private Organization createOrganizationMock(String name, boolean approved,
                                                String changePending) {
        Organization organization = mock(Organization.class);

        when(organization.getName()).thenReturn(name);
        when(organization.isApproved()).thenReturn(approved);
        when(organization.getChangePending()).thenReturn(changePending);

        return organization;
    }
}

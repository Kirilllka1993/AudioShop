package by.kazimirov.command;

import by.kazimirov.command.admin.AddAlbumCommand;
import by.kazimirov.command.admin.AddArtistCommand;
import by.kazimirov.command.admin.AddGenreCommand;
import by.kazimirov.command.admin.AddTrackCommand;
import by.kazimirov.command.guest.LogInCommand;
import by.kazimirov.command.guest.RegisterCommand;
import by.kazimirov.command.user.*;
import by.kazimirov.entity.Visitor;

import java.util.EnumSet;
import java.util.Set;

/**
 *
 */
public enum CommandType {
    LOG_IN {
        {
            this.command = new LogInCommand();
            this.role = EnumSet.of(Visitor.Role.GUEST);
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
            this.role = EnumSet.of(Visitor.Role.GUEST);
        }
    },
    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLocaleCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER, Visitor.Role.GUEST);
        }
    },
    ADD_ARTIST {
        {
            this.command = new AddArtistCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN);
        }
    },
    ADD_ALBUM {
        {
            this.command = new AddAlbumCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN);
        }
    },
    ADD_TRACK{
        {
            this.command = new AddTrackCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN);
        }
    },
    ADD_GENRE{
        {
            this.command = new AddGenreCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN);
        }
    },
    ADD_TO_CART {
        {
            this.command = new AddToCartCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    LEAVE_COMMENT {
        {
          this.command = new LeaveCommentCommand();
          this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    REMOVE_FROM_CART {
        {
            this.command = new RemoveFromCart();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    MAKE_PURCHASE {
        {
            this.command = new MakePurchaseCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    CHANGE_PERSONAL_INFO {
        {
            this.command = new ChangePersonalInfoCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    CHANGE_LOGIN {
        {
            this.command = new ChangeLoginCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    CHANGE_EMAIL {
        {
            this.command = new ChangeEmailCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    CHANGE_PASSWORD {
        {
            this.command = new ChangePasswordCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    CHANGE_AVATAR {
        {
            this.command = new ChangeAvatarCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    LOAD_TRACK {
        {
            this.command = new LoadTrackCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    SHOW_ELEMENT {
        {
            this.command = new ShowElementCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    SWITCH_PAGE {
        {
            this.command = new SwitchPageCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    },
    LOG_OUT {
        {
            this.command = new LogOutCommand();
            this.role = EnumSet.of(Visitor.Role.ADMIN, Visitor.Role.USER);
        }
    };

    public IServletCommand command;
    public Set<Visitor.Role> role;

    public IServletCommand getCurrentCommand() {
        return command;
    }
}

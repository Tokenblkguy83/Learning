#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""

APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
darwin=false
case "$(uname)" in
    Darwin*) darwin=true ;;
esac

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
while [ -h "$PRG" ] ; do
    ls=$(ls -ld "$PRG")
    link=$(expr "$ls" : '.*-> \(.*\)$')
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=$(dirname "$PRG")/"$link"
    fi
done
SAVED=$(pwd)
cd "$(dirname "$PRG")" || exit
APP_HOME=$(pwd -P)
cd "$SAVED" || exit

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD=$(command -v java)
    if [ -z "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
fi

# Increase the maximum file descriptors if we can.
if [ "$MAX_FD" != "maximum" ] ; then
    # In POSIX sh, ulimit -H must be called with a numeric argument
    MAX_FD_LIMIT=$(ulimit -H -n)
    if [ "$?" -eq 0 ] ; then
        if [ "$MAX_FD" -gt "$MAX_FD_LIMIT" ] ; then
            warn "WARNING: The specified maximum number of file descriptors ($MAX_FD) exceeds the hard limit ($MAX_FD_LIMIT)."
            MAX_FD=$MAX_FD_LIMIT
        fi
    else
        warn "WARNING: Could not query the system maximum file descriptor limit."
    fi

    ulimit -n "$MAX_FD" || warn "WARNING: Could not set maximum file descriptor limit: $MAX_FD"
else
    # Set the maximum file descriptors to the default value
    ulimit -n unlimited || warn "WARNING: Could not set maximum file descriptor limit: unlimited"
fi

# Escape application args
save () {
    for i; do
        printf "%s\n" "$i"
    done
}

APP_ARGS=$(save "$@")

# Collect all arguments for the java command, following the shell quoting and escaping rules
eval set -- "$DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS -classpath \"$CLASSPATH\" org.gradle.wrapper.GradleWrapperMain \"$APP_ARGS\""

# By default, Gradle will use the JVM options defined in the environment variables JAVA_OPTS and GRADLE_OPTS.
# You can also define JVM options in the gradle.properties file in the project directory or in the GRADLE_USER_HOME directory.
# For more information, see https://docs.gradle.org/current/userguide/build_environment.html

exec "$JAVACMD" "$@"

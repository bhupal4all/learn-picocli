# Java Command Line Interface - Todo Application

## References

## Create a Java Maven Project

## Add Dependencies

```xml
    <dependency>
        <groupId>info.picocli</groupId>
        <artifactId>picocli</artifactId>
        <version>4.6.1</version>
    </dependency>
```

## Annotations

### `CommandLine.Command`

| Property | Description |
| --- | --- |
| `name` | Name of the command (must be unique) | 
| `version` | Version of the command |
| `mixinStandardHelpOptions` | To default `help` and `version` options to command |
| `requiredOptionMarker` | To show required fields with a character  such as `*` |
| `description` | Command Description |
| `header` | Command Header |
| `optionListHeading` | Options Heading |

`%n` would be used to add a New Line

### `CommandLine.Option`

Name | Description
---|---
`names` | names of the options (must be unique)
`required` | Required Option
`description` | Description of the Option
`paramLabel` | Option Label
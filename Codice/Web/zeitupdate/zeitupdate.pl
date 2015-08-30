#!/usr/bin/perl

#@tix weather updater :D
use strict;
use warnings;
use DBI();
use Data::Dumper;
use XML::Simple;
use LWP::Simple;


# MYSQL CONFIG VARIABLES
my $host = "boscolonata.com";
my $database = "boscolon_spring";
my $user = "boscolon_shike";
my $pw = "=?wlDX!cTnn!";

  # Connect to the database.
my $dbh = DBI->connect("DBI:mysql:database=$database;host=$host",
                         $user, $pw,
                         {'RaiseError' => 1});
						 
						 
#pulizia vecchie richieste di connessione dispositivo
my $sql = 'DELETE FROM tokens WHERE account_id IS NULL AND timestamp < DATE_SUB(NOW(), INTERVAL 10 MINUTE);';
$dbh->do($sql);

#pulizia 
$sql = 'DELETE FROM weatherforecasts WHERE date < UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 2 DAY));';				
$dbh->do($sql);
						 
$sql = "select track_id, name, CenterLat, CenterLon from virtualtracks vt 
JOIN 
(SELECT track_id, ((MAX(latitude) + MIN(latitude))/2) as CenterLat, ((MAX(longitude) + MIN(longitude))/2) as CenterLon  
FROM virtualtracklocations
GROUP BY track_id
) as 
vtl ON (vt._id = vtl.track_id);";

my $sth = $dbh->prepare($sql);
$sth->execute();
while (my $r = $sth->fetchrow_hashref()) {
	print "update percorso: " . $r->{'name'} . "\n";

	my $parser = new XML::Simple;
	my $url = 'http://api.openweathermap.org/data/2.5/forecast?lat='. $r->{'CenterLat'} .'&lon='. $r->{'CenterLon'} .'&mode=xml';
	my $content = get $url or die "Unable to get $url\n";
	my $data = $parser->XMLin($content);

	for (@{ $data->{forecast}{time} }) {
		my $insstr = "REPLACE INTO weatherforecasts(virtualTrack_id, temperature, pressure, date, forecast_id, humidity, windDirection, windAvgSpeed, windMaxSpeed) VALUES ";
		$insstr .= "(" . $r->{'track_id'};
		$insstr .= ", " . $_->{temperature}{value};
		$insstr .= ", " . $_->{pressure}{value};
		$insstr .= ", UNIX_TIMESTAMP(STR_TO_DATE('" . $_->{from} . "','%Y-%m-%dT%H:%i:%s'))";
		$insstr .= ", " . $_->{symbol}{number};
		$insstr .= ", " . $_->{humidity}{value};
		$insstr .= ", " . $_->{windDirection}{deg};
		$insstr .= ", " . $_->{windSpeed}{mps};
		$insstr .= ", " . $_->{windSpeed}{mps} . ");";
		
		#print $insstr . "\n";
		$dbh->do($insstr);
	}
	
}
$sth->finish();
$dbh->disconnect();

print "update completato"


